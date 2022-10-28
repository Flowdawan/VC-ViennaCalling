package at.deflow.viennacalling.screens.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.EventDetails
import at.deflow.viennacalling.widgets.FavoriteButton
import at.deflow.viennacalling.widgets.checkIfLightModeIcon
import at.deflow.viennacalling.widgets.checkIfLightModeText
import coil.compose.AsyncImage
import coil.request.ImageRequest

private const val TAG = "EventDetailScreen"


@Composable
fun EventDetailScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel,
    eventsViewModel: EventsViewModel,
    eventId: String? = eventsViewModel.getAllEvents()[0].id,
) {
    val event = filterEvent(eventId, eventsViewModel.eventList)
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        },
        topBar = {
            TopAppBar({
                Image(
                    painterResource(checkIfLightModeIcon()),
                    contentDescription = "Vienna Calling Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(133.dp)
                        .height(57.dp)
                        .clickable {
                            navController.navigate(route = AppScreens.HomeScreen.name)
                        }
                )
            },
                backgroundColor = MaterialTheme.colors.secondary,
            )
        }
    ) { padding ->
        MainContent(event = event, favoritesViewModel = favoritesViewModel, padding = padding)
    }

}

@Composable
fun MainContent(event: Event, favoritesViewModel: FavoritesViewModel, padding: PaddingValues) {
    var isInListColor by remember {
        if (favoritesViewModel.isEventInList(event)) {
            mutableStateOf(Purple700)
        } else {
            mutableStateOf(Color.DarkGray)
        }
    }
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
                            contentDescription = "Event Cover",
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
                            isAlreadyInListColor = isInListColor,
                            onFavoriteClick = { event ->
                                if (favoritesViewModel.isEventInList(event)) {
                                    favoritesViewModel.removeEvent(event)
                                    isInListColor = Color.DarkGray
                                } else {
                                    favoritesViewModel.addEvent(event)
                                    isInListColor = Purple700
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

fun filterEvent(eventId: String?, eventList: List<Event>): Event {
    return eventList.filter { event -> event.id == eventId }[0]
}
