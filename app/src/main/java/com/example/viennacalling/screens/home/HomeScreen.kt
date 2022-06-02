package com.example.viennacalling.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.models.Event
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.viewmodels.EventsViewModel
import com.example.viennacalling.viewmodels.FavoritesViewModel
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.EventRow
import com.example.viennacalling.widgets.FavoriteButton
import com.example.viennacalling.widgets.checkIfLightModeIcon
import com.example.viennacalling.widgets.checkIfLightModeText

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    eventsViewModel: EventsViewModel = viewModel()
)
{
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
                    IconButton(onClick = {
                        navController.navigate(route = AppScreens.FavoriteScreen.name)
                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { padding ->
        MainContent(navController = navController,
            padding = padding,
            favoritesViewModel = favoritesViewModel,
            eventsViewModel = eventsViewModel
            )
    }
}

@Composable
fun MainContent(navController: NavController,
                padding: PaddingValues,
                favoritesViewModel: FavoritesViewModel,
                eventsViewModel: EventsViewModel
                ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    5.dp,
                    2.dp,
                    padding.calculateTopPadding(),
                    padding.calculateBottomPadding()
                )
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = eventsViewModel.getAllEvents()) { event ->
            EventRow(
                event = event,
                onItemClick = { eventId ->
                    navController.navigate(route = AppScreens.EventDetailScreen.name + "/$eventId")
                }){
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

