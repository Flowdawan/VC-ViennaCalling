package com.example.viennacalling.navigation.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.viennacalling.R
import com.example.viennacalling.navigation.AppScreens

sealed class BottomNavItem(var title:String, var icon: Int, var screen_route:String){
    object Home : BottomNavItem("Home", R.drawable.ic_vienna_calling_logo_splash_round, AppScreens.HomeScreen.name)
    object Filter: BottomNavItem("Filter", R.drawable.ic_vienna_calling_logo_splash_round, AppScreens.FilterScreen.name)
    object Favorites: BottomNavItem("Favorites", R.drawable.ic_vienna_calling_logo_splash_round, AppScreens.FavoriteScreen.name)
    object Account: BottomNavItem("Account", R.drawable.ic_vienna_calling_logo_splash_round, AppScreens.LoginScreen.name)
}
