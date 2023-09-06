package com.example.weatherapp.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController

data class NavigationItem(
    val itemIcon: ImageVector,
    val title: String,
    val onItemClick: (NavController) -> Unit
)
