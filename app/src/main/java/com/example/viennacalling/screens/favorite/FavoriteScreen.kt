package com.example.viennacalling.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.ui.theme.VcNavTopBottom
import com.example.viennacalling.viewmodels.FavoritesViewModel
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.EventRow
import com.example.viennacalling.widgets.checkIfLightModeIcon

@Composable
fun FavoriteScreen(navController: NavController = rememberNavController(),
                   favoritesViewModel: FavoritesViewModel = viewModel(),
                   loginViewModel: LoginViewModel
                   ) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = { BottomNavigationBar(navController = navController, loginViewModel = loginViewModel) },
        topBar = {
            TopAppBar({
                Image(
                    painterResource(checkIfLightModeIcon()),
                    contentDescription = "Vienna Calling Logo",
                    contentScale = ContentScale.Crop
                )
            },
                backgroundColor = MaterialTheme.colors.secondary,
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) {
        MainContent(navController = navController, favoritesViewModel = favoritesViewModel)
    }
}

@Composable
fun MainContent(navController: NavController,
                events: List<Event> = getEvents(),
                favoritesViewModel: FavoritesViewModel
) {
    LazyColumn {
        items(items = favoritesViewModel.getAllEvents()) { event ->
            EventRow(event = event,
                onItemClick = { eventId ->
                    navController.navigate(route =  AppScreens.EventDetailScreen.name + "/$eventId")
                }
            )
        }
    }
}