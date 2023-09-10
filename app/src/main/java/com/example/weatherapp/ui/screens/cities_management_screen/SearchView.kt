package com.example.weatherapp.ui.screens.cities_management_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.TextNormalStyle
import com.google.android.libraries.places.api.model.AutocompletePrediction

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
