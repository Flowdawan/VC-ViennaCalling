package com.example.viennacalling.screens.home

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    MainContent(navController = navController)
}

@Composable
fun MainContent(navController: NavController) {
    Text(text = "Hello Vienna!")
}