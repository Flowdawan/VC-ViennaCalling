package com.example.viennacalling.navigation.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.viennacalling.navigation.AppScreens

sealed class BottomNavItem(var title: String, var icon: ImageVector, var screen_route: String) {

    object Home : BottomNavItem("Startseite", Icons.Default.Home, AppScreens.HomeScreen.name)
    object Filter : BottomNavItem("Filtern", Icons.Default.Search, AppScreens.FilterScreen.name)
    object Account : BottomNavItem("Konto", Icons.Default.AccountBox, AppScreens.AccountScreen.name)
    object Settings : BottomNavItem("Settings", Icons.Default.Settings, AppScreens.SettingsScreen.name)
}
