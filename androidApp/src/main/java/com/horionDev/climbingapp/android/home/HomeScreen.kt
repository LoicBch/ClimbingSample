package com.horionDev.climbingapp.android.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.horionDev.climbingapp.android.LocalDependencyContainer
import com.horionDev.climbingapp.android.NavGraphs
import com.horionDev.climbingapp.android.composables.*
import com.horionDev.climbingapp.domain.model.composition.Location
import com.horionDev.climbingapp.managers.location.LocationManager
import com.horionDev.climbingapp.utils.*
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.flow.*
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {

    val appViewModel = LocalDependencyContainer.current.appViewModel
    val sheetState = appViewModel.bottomSheetIsShowing
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val popupState by appViewModel.globalPopup.collectAsState()
    val networkIsObserved by appViewModel.networkIsObserved.collectAsState()
    val observersAreSet by appViewModel.observersAreSet.collectAsState()

    DisposableEffect(lifecycleOwner) {
        LocationManager.onLocationUpdated(this) {
            appViewModel.removeGpsMissingNotification()
            val lat = it.coordinates.latitude
            val long = it.coordinates.longitude
            Globals.GeoLoc.lastKnownLocation = Location(lat, long)

            if (!appViewModel.locationIsObserved.value) {
                appViewModel.onLocationObserveStarted()
            }
        }.onLocationUnavailable(this) {
            if (!appViewModel.locationIsObserved.value) {
                Globals.GeoLoc.lastKnownLocation = Constants.DEFAULT_LOCATION
                Globals.GeoLoc.lastSearchedLocation = Constants.DEFAULT_LOCATION
                appViewModel.onLocationObserveStarted()
            }
            appViewModel.showGlobalSlider(GlobalSliderState.GPS_MISSING)
        }.startLocationUpdating()
        appViewModel.onLocationObserveStarted()

        NetworkConnectivityObserver(context).observe().onEach {
            Globals.network.status = it

            if (it == ConnectivityObserver.NetworkStatus.Lost || it == ConnectivityObserver.NetworkStatus.Unavailable) {
                appViewModel.showGlobalSlider(GlobalSliderState.NETWORK_MISSING)
            } else {
                appViewModel.removeNetworkMissingNotification()
            }
        }.onStart {
            if (Globals.network.status == ConnectivityObserver.NetworkStatus.Unavailable) {
                appViewModel.showGlobalSlider(GlobalSliderState.NETWORK_MISSING)
            }
            if (!networkIsObserved) appViewModel.onNetworkObserveStarted()
        }.launchIn(lifecycleOwner.lifecycleScope)

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> Log.d("lifecycle", "onStart")
                Lifecycle.Event.ON_START -> Log.d("lifecycle", "onStart")
                Lifecycle.Event.ON_RESUME -> Log.d("lifecycle", "onResume")
                Lifecycle.Event.ON_PAUSE -> Log.d("lifecycle", "onPause")
                Lifecycle.Event.ON_STOP -> Log.d("lifecycle", "onStop")
                Lifecycle.Event.ON_DESTROY -> Log.d("lifecycle", "onDestroy")
                Lifecycle.Event.ON_ANY -> Log.d("lifecycle", "onAny")
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (observersAreSet) {
        AppScaffold(
            sheetState = sheetState,
            bottomBar = { BottomBar(navController = navController) },
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                DestinationsNavHost(
                    modifier = Modifier.padding(it),
                    navController = navController,
                    navGraph = NavGraphs.root,
                    startRoute = NavGraphs.root.startRoute
                )

                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.Center),
                    visible = popupState != GlobalPopupState.HID,
                    enter = fadeIn(), exit = fadeOut()
                ) {
                    GlobalPopup(
                        Modifier, popupState
                    ) {
                        appViewModel.hideGlobalPopup()
                    }
                }

                GlobalSliderModal(
                    sliderState = appViewModel.globalSlider,
                    onRemoveGpsNotification = { appViewModel.removeGpsMissingNotification() }
                ) { appViewModel.removeNetworkMissingNotification() }
            }
        }
    }
}



