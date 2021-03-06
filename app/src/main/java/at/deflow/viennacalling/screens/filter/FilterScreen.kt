package at.deflow.viennacalling.screens.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.*

private const val TAG = "FilterScreen"

@Composable
fun FilterScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel,
    eventsViewModel: EventsViewModel
) {
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
                )
            },
                backgroundColor = MaterialTheme.colors.secondary,
                actions = {
                    IconButton(onClick = {
                        navController.navigate(route = AppScreens.FavoriteScreen.name)
                    }) {
                        Icon(
                            tint = checkIfLightModeText(),
                            imageVector = Icons.Default.FavoriteBorder,
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
            eventsViewModel = eventsViewModel,
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    padding: PaddingValues,
    eventsViewModel: EventsViewModel
) {
    var filteredEventList = eventsViewModel.getAllFilteredEvents()
    var initialState by remember {
        mutableStateOf(true)
    }
    var clickedButton by remember {
        mutableStateOf(0)
    }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .height(60.dp)
                .width(130.dp)
                .padding(end = 5.dp),
            border = if (clickedButton == 1) {
                BorderStroke(1.dp, Color.DarkGray)
            } else {
                BorderStroke(1.dp, Color.Black)
            },
            shape = RoundedCornerShape(44),

            onClick = {
                initialState = false
                clickedButton = 1
                filteredEventList =
                    eventsViewModel.filterEventsByCategory(categoryId = "68", subCategory = "68")
            }
        ) {
            Text(
                text = "Kultur & Freizeit",
                style = MaterialTheme.typography.subtitle1,
                color = checkIfLightModeText(),
            )
        }

        Button(
            modifier = Modifier
                .height(60.dp)
                .width(130.dp)
                .padding(end = 5.dp),
            border = if (clickedButton == 2) {
                BorderStroke(1.dp, Color.DarkGray)
            } else {
                BorderStroke(1.dp, Color.Black)
            },
            shape = RoundedCornerShape(44),
            onClick = {
                initialState = false
                clickedButton = 2
                filteredEventList = eventsViewModel.filterEventsByCategory(
                    categoryId = "73",
                    subCategory = "90,+64,+91,+73"
                )
            }
        ) {
            Text(
                text = "Sehensw??rdigkeiten",
                color = checkIfLightModeText(),
            )
        }

        Button(
            modifier = Modifier
                .height(60.dp)
                .width(130.dp),
            border = if (clickedButton == 3) {
                BorderStroke(1.dp, Color.DarkGray)
            } else {
                BorderStroke(1.dp, Color.Black)
            },
            shape = RoundedCornerShape(44),
            onClick = {
                initialState = false
                clickedButton = 3
                filteredEventList =
                    eventsViewModel.filterEventsByCategory(categoryId = "6", subCategory = "")
            }
        ) {
            Text(
                text = "Party",
                color = checkIfLightModeText(),
            )
        }

    }
    Divider(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(20.dp)
            .alpha(alpha = 0.2F)
    )

    if (!initialState) {
        CircularIndeterminatorProgressBar(isDisplayed = filteredEventList.isEmpty())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                PaddingValues(
                    5.dp,
                    2.dp + 80.dp,
                    padding.calculateTopPadding(),
                    padding.calculateBottomPadding()
                )
            ),

        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = filteredEventList) { event ->
            var isInListColor by remember {
                if (favoritesViewModel.isEventInList(event)) {
                    mutableStateOf(Purple700)
                } else {
                    mutableStateOf(Color.DarkGray)
                }
            }
            EventRow(
                event = event,
                onItemClick = { eventId ->
                    navController.navigate(route = AppScreens.EventDetailScreen.name + "/$eventId")
                }) {
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