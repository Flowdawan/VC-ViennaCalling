package com.example.viennacalling.screens.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.viennacalling.models.Event
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.viewmodels.EventsViewModel
import com.example.viennacalling.viewmodels.FavoritesViewModel
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.EventDetails
import com.example.viennacalling.widgets.FavoriteButton
import com.example.viennacalling.widgets.checkIfLightModeIcon

private const val TAG = "EventDetailScreen"


@Composable
fun EventDetailScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel,
    loginViewModel: LoginViewModel = viewModel(),
    eventsViewModel: EventsViewModel,
    eventId: String? = eventsViewModel.getAllEvents()[0].id,
) {
    val event = filterMovie(eventId, eventsViewModel.eventList)
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
    ) {
        MainContent(event = event, favoritesViewModel = favoritesViewModel)
    }

}

@Composable
fun MainContent(event: Event, favoritesViewModel: FavoritesViewModel = viewModel()) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Column(
                    modifier = Modifier.background(color = MaterialTheme.colors.onBackground)
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(event.images)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Movie Cover",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.surface,
                        modifier = Modifier
                            .padding(10.dp)
                            .alpha(alpha = 0.6F)
                    )
                    EventDetails(event = event) {
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
    }
}

fun filterMovie(eventId: String?, eventList: List<Event>): Event {
    return eventList.filter { event -> event.id == eventId }[0]
}
