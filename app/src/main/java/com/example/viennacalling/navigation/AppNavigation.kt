package com.example.viennacalling.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.screens.home.HomeScreen
import com.example.viennacalling.screens.splash.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
        composable(AppScreens.HomeScreen.name) { HomeScreen(navController = navController) }
        composable(route = AppScreens.SplashScreen.name) { SplashScreen(navController = navController) }
    }
}