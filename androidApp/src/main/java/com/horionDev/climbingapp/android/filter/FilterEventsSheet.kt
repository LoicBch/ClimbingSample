package com.horionDev.climbingapp.android.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.horionDev.climbingapp.android.LocalDependencyContainer
import com.horionDev.climbingapp.android.R
import com.horionDev.climbingapp.android.composables.AppButton
import com.horionDev.climbingapp.android.composables.SearchField
import com.horionDev.climbingapp.android.composables.collectAsStateWithLifecycleImmutable
import com.horionDev.climbingapp.android.ui.theme.AppColor
import com.horionDev.climbingapp.android.ui.theme.Dimensions
import com.horionDev.climbingapp.domain.model.composition.filterName
import com.horionDev.climbingapp.utils.Globals
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterEventsSheet() {

    val appViewmodel = LocalDependencyContainer.current.appViewModel
    var countriesSelected by remember { mutableStateOf("") }
    var isSelectingCountry by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val sheetState = appViewmodel.bottomSheetIsShowing


    if (!appViewmodel.bottomSheetIsShowing.isVisible) isSelectingCountry = false

    if (isSelectingCountry) {
        SelectCountryLayout(onValidSelection = {
            countriesSelected = it
            isSelectingCountry = false
        }, onSelectingCountryCancel = { isSelectingCountry = false })
    } else {
        BaseLayout(
            countrySelected = countriesSelected,
            onButtonClick = { isSelectingCountry = true },
            onLastSearchSelect = { countriesSelected = it },
            onApplyFilter = {
                appViewmodel.applyFilterToEvents(countriesSelected)
                coroutine.launch { sheetState.hide() }
            }
        )
    }
}

@Composable
fun SelectCountryLayout(
    onValidSelection: (String) -> Unit, onSelectingCountryCancel: (Boolean) -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    val countriesSelected by remember {
        mutableStateOf(mutableListOf(""))
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onSelectingCountryCancel(false) }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = AppColor.Tertiary
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = stringResource(id = R.string.choose_country),
                fontWeight = FontWeight.W500,
                fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.circularstdmedium)),
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(0.5f))
            IconButton(modifier = Modifier.alpha(0f), onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = AppColor.Tertiary
                )
            }
        }

        SearchField(modifier = Modifier.padding(top = 22.dp),
                    placeHolder = R.string.search_placeholder,
                    onUserSearch = { textState.value = it })

        val items = if (textState.value.text.isEmpty()) {
            Globals.filters.countries
        } else {
            Globals.filters.countries.filter {
                it.lowercase()
                    .startsWith(textState.value.text.lowercase())
            }.take(5)
        }

        if (textState.value.text.isEmpty()) {
            HistoricSearchList(
                onSelectFilter = onValidSelection
            )

            CountriesResultsList(countriesSelected, {
                if (countriesSelected.contains(it)) {
                    countriesSelected.remove(it)
                } else {
                    countriesSelected.add(it)
                }
            }, stringResource(id = R.string.choose_country), items)

        } else {
            CountriesResultsList(countriesSelected, {
                if (countriesSelected.contains(it)) {
                    countriesSelected.remove(it)
                } else {
                    countriesSelected.add(it)
                }
            }, stringResource(id = R.string.search_result), items)
        }

        AppButton(
            isActive = countriesSelected.isNotEmpty(),
            onClick = {
                onValidSelection(countriesSelected.joinToString(separator = ", ").drop(1))
            },
            modifier = Modifier.padding(
                bottom = 16.dp
            ),
            textRes = R.string.confirm_selection
        )
    }
}

@Composable
fun ColumnScope.CountriesResultsList(
    countriesSelected: List<String>,
    onItemClick: (String) -> Unit,
    subTitleLabel: String,
    results: List<String>
) {

    val scrollState = rememberScrollState()

    val items = remember {
        mutableStateOf(results)
    }

    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = subTitleLabel,
        color = AppColor.outlineText,
        fontSize = 14.sp,fontFamily = FontFamily(Font(R.font.circularstdmedium)),
        fontWeight = FontWeight.W500
    )

    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .padding(top = 22.dp)
            .scrollable(
                state = scrollState, orientation = Orientation.Vertical
            )
    ) {
        items(items.value) { item ->
            CountryOptionItem(
                onItemClick, item, countriesSelected.contains(item)
            )
        }
    }
}

@Composable
fun CountryOptionItem(onButtonClick: (String) -> Unit, option: String, selected: Boolean) {

    var checkedState by remember { mutableStateOf(selected) }

    Row(
        modifier = Modifier
            .height(Dimensions.buttonHeight)
            .fillMaxWidth()
            .clickable {
                onButtonClick(option)
                checkedState = !checkedState
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = AppColor.Tertiary)

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = option,fontFamily = FontFamily(Font(R.font.circularstdmedium)),
            fontSize = 16.sp,
            fontWeight = FontWeight(450)
        )

        Spacer(modifier = Modifier.weight(1f))

        Checkbox(checked = checkedState,
                 enabled = true,
                 colors = CheckboxDefaults.colors(checkedColor = AppColor.Primary),
                 onCheckedChange = {
                     onButtonClick(option)
                     checkedState = !checkedState
                 })
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseLayout(
    countrySelected: String, onButtonClick: () -> Unit,
    onLastSearchSelect: (String) -> Unit,
    onApplyFilter: (String) -> Unit
) {

    val sheetState = LocalDependencyContainer.current.appViewModel.bottomSheetIsShowing
    val coroutine = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(top = 30.dp, start = 21.dp, end = 21.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(onClick = { coroutine.launch { sheetState.hide() } }) {
                Icon(
                    imageVector = Icons.Sharp.Close,
                    contentDescription = "",
                    tint = AppColor.Tertiary
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                fontSize = 16.sp, fontWeight = FontWeight.W500,fontFamily = FontFamily(Font(R.font.circularstdmedium)), text = stringResource(
                    id = R.string.filters_title
                )
            )
            Spacer(modifier = Modifier.weight(0.5f))
            IconButton(modifier = Modifier.alpha(0f), onClick = { }) {
                Icon(
                    imageVector = Icons.Sharp.Close,
                    contentDescription = "",
                    tint = AppColor.Tertiary
                )
            }

        }

        Text(
            modifier = Modifier.padding(top = 60.dp),
            fontSize = 22.sp,fontFamily = FontFamily(Font(R.font.circularstdmedium)),
            fontWeight = FontWeight.W700,
            text = stringResource(
                id = R.string.where_do_you_want_to_go
            )
        )

        Divider(modifier = Modifier.padding(top = 13.dp, bottom = 20.dp))

        if (countrySelected.isNotEmpty()) {
            MaterialTextField(
                onSelectButtonClick = { onButtonClick() }, countrySelected = countrySelected
            )
        } else {
            FilterOptionsButton(
                onSelectButtonClick = { onButtonClick() },
                buttonLabel = stringResource(id = R.string.choose_country)
            )
        }

        HistoricSearchList(onSelectFilter = onLastSearchSelect)

        Spacer(modifier = Modifier.weight(1f))

        AppButton(isActive = true, onClick = {
            onApplyFilter(countrySelected)
        }, modifier = Modifier.padding(bottom = 16.dp), textRes = R.string.apply_filters)
    }
}

@Composable
fun FilterOptionsButton(
    onSelectButtonClick: () -> Unit, buttonLabel: String
) {

    Button(modifier = Modifier.height(Dimensions.buttonHeight),
           colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
           shape = RoundedCornerShape(Dimensions.radiusTextField),
           border = BorderStroke(2.dp, AppColor.Tertiary),
           onClick = { onSelectButtonClick() }) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = buttonLabel,fontFamily = FontFamily(Font(R.font.circularstdmedium)),
                color = AppColor.Tertiary,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(end = 12.dp),
                painter = painterResource(id = R.drawable.unfold),
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MaterialTextField(
    onSelectButtonClick: (String) -> Unit, countrySelected: String
) {

    BasicTextField(
        value = countrySelected,
        modifier = Modifier
            .height(Dimensions.buttonHeight)
            .fillMaxWidth()
            .clickable { onSelectButtonClick(countrySelected) },
        onValueChange = { },
        enabled = false,
        readOnly = true,
        textStyle = TextStyle(
            color = AppColor.Primary,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp
        ),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = countrySelected,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = null,
                interactionSource = remember { MutableInteractionSource() },
                label = {
                    Text(
                        text = "countries",
                        fontWeight = FontWeight.W500,
                        fontSize = 11.sp,fontFamily = FontFamily(Font(R.font.circularstdmedium)),
                        color = AppColor.Primary
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unfold),
                        contentDescription = "", tint = AppColor.Primary
                    )
                },
                singleLine = true,
                enabled = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = AppColor.Primary,
                    focusedBorderColor = AppColor.Primary,
                    unfocusedBorderColor = AppColor.Primary
                ),
                border = {
                    TextFieldDefaults.BorderBox(
                        false,
                        isError = false,
                        interactionSource = remember { MutableInteractionSource() },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = AppColor.Primary,
                            focusedBorderColor = AppColor.Primary,
                            unfocusedBorderColor = AppColor.Primary
                        ),
                        focusedBorderThickness = 2.dp,
                        unfocusedBorderThickness = 2.dp
                    )
                }
            )
        }
    )
}

// TODO: make this composable unique merge it with the one used in filter for dealers: Make a list of possible search history type
@Composable
fun HistoricSearchList(onSelectFilter: (String) -> Unit) {

    val appViewmodel = LocalDependencyContainer.current.appViewModel
    val searches by appViewmodel.filtersEventUsed.collectAsStateWithLifecycleImmutable()
    val scrollState = rememberScrollState()

    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = stringResource(id = R.string.last_searched),
        color = AppColor.outlineText,
        fontSize = 14.sp,fontFamily = FontFamily(Font(R.font.circularstdmedium)),
        fontWeight = FontWeight.W500
    )

    LazyColumn(
        modifier = Modifier
            .padding(top = 22.dp)
            .scrollable(
                state = scrollState, orientation = Orientation.Vertical
            )
    ) {
        items(searches.value) { filter ->
            LastSearchItem(onSearchDelete = {
                appViewmodel.removeFilter(filter)
            }, onSelectSearch = {
                onSelectFilter(it)
            }, search = filter.filterName)
        }
    }
}
