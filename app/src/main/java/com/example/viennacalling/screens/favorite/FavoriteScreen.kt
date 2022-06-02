package com.example.viennacalling.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.models.Event
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.viewmodels.FavoritesViewModel
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.EventRow
import com.example.viennacalling.widgets.FavoriteButton
import com.example.viennacalling.widgets.checkIfLightModeIcon

@Composable
fun FavoriteScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel,
    loginViewModel: LoginViewModel
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                loginViewModel = loginViewModel
            )
        },
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
    ) { padding ->
        MainContent(
            navController = navController,
            favoritesViewModel = favoritesViewModel,
            padding = padding,
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    padding: PaddingValues,
) {
    val eventList: List<Event> by favoritesViewModel.favoriteEvents.collectAsState()
    LazyColumn(
        modifier = Modifier.padding(
            PaddingValues(
                5.dp,
                2.dp,
                padding.calculateTopPadding(),
                padding.calculateBottomPadding()
            )
        ),
    ) {
        items(items = eventList) { event ->
            EventRow(event = event,
                onItemClick = { eventId ->
                    navController.navigate(route = AppScreens.EventDetailScreen.name + "/$eventId")
                }
            ) {
                FavoriteButton(
                    event = event,
                    isAlreadyInList = favoritesViewModel.isEventInList(event),
                    onFavoriteClick = { event ->
                        if (favoritesViewModel.isEventInList(event)) {
                            favoritesViewModel.removeEvent(event)
                        } else {
                            favoritesViewModel.addEvent(event)
                        }
                    }

                )
            }
        }
    }
}