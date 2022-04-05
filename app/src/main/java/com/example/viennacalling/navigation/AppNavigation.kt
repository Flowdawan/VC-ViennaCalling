package com.example.viennacalling.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.screens.home.HomeScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {

        composable(AppScreens.HomeScreen.name) { HomeScreen() }
    }
}