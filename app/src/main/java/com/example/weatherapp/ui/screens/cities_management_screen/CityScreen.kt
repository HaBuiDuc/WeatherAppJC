package com.example.weatherapp.ui.screens.cities_management_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.screens.shared.SimpleTopBar
import com.example.weatherapp.ui.theme.DodgerBlue
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.example.weatherapp.ui.theme.TextSemiBoldStyle
import com.example.weatherapp.viewmodel.CitiesViewModel
import com.google.android.libraries.places.api.model.AutocompletePrediction

@Preview
@Composable
fun CityScreenPreview() {
    CityScreen(navController = rememberNavController())
}

@Composable
fun CityScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CitiesViewModel = viewModel()
) {
    val context = LocalContext.current
    val citiesState by viewModel.citiesState.collectAsState()
    viewModel.getLocations(context)

    var selectedItemLocation by remember {
        mutableStateOf(emptySet<String>())
    }

    val isSelectModeOn by remember {
        derivedStateOf { selectedItemLocation.isNotEmpty() }
    }
    Scaffold(
        topBar = {
            SimpleTopBar {
                navController.popBackStack()
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.city_management),
                    style = TextNormalStyle,
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                SearchView(
                    placeholderText = stringResource(id = R.string.input_location),
                    locationQuery = citiesState.locationQuery,
                    suggestionItems = citiesState.suggestionItems,
                    onQueryChangeListener = {
                        viewModel.onQueryChange(it)
                        viewModel.getLocationPrediction(
                            context,
                            it
                        )
                    },
                    onAddLocationListener = { location ->
                        viewModel.addLocation(context, location.getFullText(null).toString())
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(citiesState.locationList.map { it.substringBefore(",") }) { location ->
                        CityItem(
                            location = location,
                            isSelectModeOn = isSelectModeOn,
                            onItemSelectedListener = { locationItem, isSelected ->
                                if (isSelected) {
                                    selectedItemLocation += citiesState.locationList.find { it.contains(locationItem) }!!
                                } else {
                                    selectedItemLocation -= citiesState.locationList.find { it.contains(locationItem) }!!
                                }
                            }
                        )
                    }
                }
            }
            if (isSelectModeOn) {
                Icon(
                    imageVector = Icons.Filled.DeleteOutline,
                    contentDescription = stringResource(id = R.string.image_des),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(Color.White, shape = CircleShape)
                        .padding(bottom = 24.dp)
                        .size(32.dp)
                        .clickable {
                            for (location in selectedItemLocation) {
                                viewModel.deleteLocation(context, location)
                            }
                            selectedItemLocation = emptySet()
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityItem(
    location: String,
    isSelectModeOn: Boolean,
    onItemSelectedListener: (String, Boolean) -> Unit
) {
    var isToggle by remember {
        mutableStateOf(false)
    } // Variable to check if an item is selected
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .border(
                border = BorderStroke(0.5.dp, Color.Black),
                shape = RoundedCornerShape(10.dp)
            )
            .combinedClickable(
                onLongClick = {
                    isToggle = !isToggle
                    onItemSelectedListener(location, true)
                },
                onClick = {
                    if (isSelectModeOn) {
                        isToggle = !isToggle
                        onItemSelectedListener(location, isToggle)
                    }
                }
            )
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
    ) {

        Text(
            text = location,
            style = TextSemiBoldStyle,
            fontSize = 22.sp,
            maxLines = 2,
            softWrap = true,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        IconToggleButton(
            checked = isToggle,
            onCheckedChange = {
                isToggle = it
                onItemSelectedListener(location, isToggle)
            }
        ) {
            if (isSelectModeOn) {
                Icon(
                    imageVector = if (isToggle) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                    contentDescription = stringResource(id = R.string.image_des),
                    tint = DodgerBlue,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun SuggestionItem(
    location: AutocompletePrediction,
    onAddLocationListener: () -> Unit
) {
    var isAdd by remember {
        mutableStateOf(false)
    } // variable to check if an item is added to location list or not
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = location.getPrimaryText(null).toString(),
                style = TextSemiBoldStyle,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = location.getSecondaryText(null).toString(),
                style = TextNormalStyle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        if (!isAdd) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.image_des),
                modifier = Modifier
                    .clickable {
                        onAddLocationListener()
                        isAdd = true
                    }
            )
        } else {
            Text(text = "Added")
        }
    }
}