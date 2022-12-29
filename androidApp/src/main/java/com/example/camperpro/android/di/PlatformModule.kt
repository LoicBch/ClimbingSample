package com.example.camperpro.android.di

import com.example.camperpro.android.AppViewModel
import com.example.camperpro.android.aroundLocation.AroundLocationViewModel
import com.example.camperpro.android.mainmap.MainMapViewModel
import com.example.camperpro.android.onBoarding.SplashScreenViewModel
import com.example.camperpro.data.datasources.local.DatabaseDriverFactory
import com.example.camperpro.data.datasources.local.LocationSearchDaoDelight
import com.example.camperpro.data.datasources.local.SearchDaoDelight
import com.example.camperpro.data.datasources.local.dao.LocationSearchDao
import com.example.camperpro.data.datasources.local.dao.SearchDao
import com.example.camperpro.database.CamperproDatabase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.get
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


fun platformModule() = listOf(viewModelModule, persistenceModule)


val viewModelModule = module {
    viewModelOf(::AppViewModel)
    viewModelOf(::MainMapViewModel)
    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::AroundLocationViewModel)
}

val persistenceModule = module {
    singleOf<CamperproDatabase> {
        CamperproDatabase(DatabaseDriverFactory(get().get()).createDriver())
    }

    singleOf<SearchDao> { SearchDaoDelight(get().get()) }
    singleOf<LocationSearchDao> { LocationSearchDaoDelight(get().get()) }
}