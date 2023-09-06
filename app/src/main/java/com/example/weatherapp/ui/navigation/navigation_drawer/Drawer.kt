package com.example.weatherapp.ui.navigation.navigation_drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.navigation.NavigationItem
import com.example.weatherapp.ui.navigation.Screen
import com.example.weatherapp.ui.theme.TextBoldStyle
import kotlinx.coroutines.launch

private val itemList = listOf(
    NavigationItem(
        title = "Add city",
        itemIcon = Icons.Filled.Add
    ) {navController ->
        navController.navigate(Screen.CityScreen.route)
    },
    NavigationItem(
        title = "Settings",
        itemIcon = Icons.Filled.Settings
    ) { navController ->
        navController.navigate(Screen.SettingsScreen.route)
    }
)

@Composable
fun Drawer(
    drawerState: DrawerState,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxHeight(0.6f)
        ) {
            Column {
                itemList.forEachIndexed { _, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                style = TextBoldStyle,
                                fontSize = 20.sp
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = item.itemIcon,
                                contentDescription = stringResource(id = R.string.image_des),
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            item.onItemClick(navController)
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.8f)
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.drawer_bg),
            contentDescription = stringResource(id = R.string.image_des)
        )
    }
}