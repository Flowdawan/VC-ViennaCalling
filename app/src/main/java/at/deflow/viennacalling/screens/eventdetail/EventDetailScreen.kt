package at.deflow.viennacalling.screens.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.CircularIndeterminatorProgressBar
import at.deflow.viennacalling.widgets.EventDetails
import at.deflow.viennacalling.widgets.FavoriteButton
import at.deflow.viennacalling.widgets.checkIfLightModeIcon
import at.deflow.viennacalling.widgets.checkIfLightModeText
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EventDetailScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    eventsViewModel: EventsViewModel,
    eventId: String? = null,
) {
    val uiState by eventsViewModel.uiState.collectAsState()
    val event = remember(eventId, uiState.events) {
        filterEvent(eventId, uiState.events)
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        },
        topBar = {
            TopAppBar(
                {
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
        if (uiState.isLoading) {
            CircularIndeterminatorProgressBar(isDisplayed = true)
        } else if (event == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Event konnte nicht geladen werden.",
                    color = checkIfLightModeText()
                )
            }
        } else {
            MainContent(event = event, favoritesViewModel = favoritesViewModel, padding = padding)
        }
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
                            .height(260.dp)
                            .background(MaterialTheme.colors.secondaryVariant)
                            .padding(bottom = 8.dp)
                            .shadow(8.dp, RoundedCornerShape(0.dp))
                    ) {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(event.images)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Event Cover",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colors.background
                                            ),
                                            startY = 100f
                                        )
                                    )
                            )
                        }
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

fun filterEvent(eventId: String?, eventList: List<Event>): Event? {
    return eventList.firstOrNull { event -> event.id == eventId }
}
