package com.example.weatherapp.ui.screens.city_management_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    Scaffold(
        topBar = {
            SimpleTopBar {
                navController.popBackStack()
            }
        }
    ) { padding ->
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
                onAddLocationListener = {location ->
                    viewModel.addLocation(context, location.getFullText(null).toString())
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    placeholderText: String,
    locationQuery: String,
    suggestionItems: List<AutocompletePrediction>,
    onQueryChangeListener: (String) -> Unit,
    onAddLocationListener: (AutocompletePrediction) -> Unit
) {
    var isActive by remember {
        mutableStateOf(false)
    }
    SearchBar(
        query = locationQuery,
        onQueryChange = {
            onQueryChangeListener(it)
        },
        onActiveChange = {
            isActive = it
        },
        onSearch = {
            isActive = false
        },
        active = isActive,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.image_des)
            )
        },
        trailingIcon = {
            if (isActive) {
                Icon(
                    modifier = Modifier.clickable {
                        if (locationQuery.isNotEmpty()) {
                            onQueryChangeListener("")
                        } else {
                            isActive = false
                        }
                    },
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.image_des)
                )
            }
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = TextNormalStyle,
                fontSize = 20.sp
            )
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        suggestionItems.forEach {item ->
            SuggestionItem(location = item) {
                onAddLocationListener(item)
            }
        }
    }
}

@Composable
fun SuggestionItem(
    location: AutocompletePrediction,
    onAddLocationListener: () -> Unit
) {
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
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.image_des),
            modifier = Modifier
                .clickable {
                    onAddLocationListener()
                }
        )
    }
}