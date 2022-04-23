package com.example.viennacalling.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.screens.eventdetail.EventDetailScreen
import com.example.viennacalling.screens.favorite.FavoriteScreen
import com.example.viennacalling.screens.filter.FilterScreen
import com.example.viennacalling.screens.home.HomeScreen
import com.example.viennacalling.screens.login.LoginScreen
import com.example.viennacalling.screens.registration.RegistrationScreen
import com.example.viennacalling.screens.setting.SettingsScreen
import com.example.viennacalling.screens.splash.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
        composable(AppScreens.HomeScreen.name) { HomeScreen(navController = navController) }
        composable(route = AppScreens.SplashScreen.name) { SplashScreen(navController = navController) }
        composable(route = AppScreens.EventDetailScreen.name) { EventDetailScreen(navController = navController) }
        composable(route = AppScreens.LoginScreen.name) { LoginScreen(navController = navController) }
        composable(route = AppScreens.RegistrationScreen.name) { RegistrationScreen(navController = navController) }
        composable(route = AppScreens.FilterScreen.name) { FilterScreen(navController = navController) }
        composable(route = AppScreens.FavoriteScreen.name) { FavoriteScreen(navController = navController) }
        composable(route = AppScreens.SettingsScreen.name) { SettingsScreen(navController = navController) }

    }
}