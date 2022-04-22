package com.example.viennacalling.screens.registration

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.screens.login.MainContent
import com.example.viennacalling.ui.theme.VcGrey

@Composable
fun RegistrationScreen(navController: NavController = rememberNavController()) {
    @Composable
    fun LoginScreen(navController: NavController = rememberNavController()) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Registration Screen") },
                    backgroundColor = VcGrey,
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(route = AppScreens.FavoriteScreen.name)
                        }) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite"
                            )
                        }
                    }
                )
            }
        ) {
            MainContent(navController = navController)
        }
    }
}

@Composable
fun MainContent(navController: NavController, events: List<Event> = getEvents(), ) {
    Text("Registration Screen")
}