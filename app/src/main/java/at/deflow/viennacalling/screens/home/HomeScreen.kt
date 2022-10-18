package at.deflow.viennacalling.screens.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.viewmodels.FilteredState
import at.deflow.viennacalling.widgets.CircularIndeterminatorProgressBar
import at.deflow.viennacalling.widgets.EventRow
import at.deflow.viennacalling.widgets.FavoriteButton
import at.deflow.viennacalling.widgets.checkIfLightModeText

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel = viewModel(),
    eventsViewModel: EventsViewModel = viewModel(),
) {
    val filteredState = eventsViewModel.eventsFilterState

    var clickedAdditionalInfo by remember {
        mutableStateOf(0)
    }

    val initialList = eventsViewModel.getAllEvents()
    val events = EventsClass(
        eventList = initialList,
        filteredEventList = initialList
    )
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        },
        topBar = {
            TopAppBar({
                DropdownMenu(
                    filteredState = filteredState,
                    events = events,
                    eventsViewModel = eventsViewModel,
                    onFilterClicked =  {
                        clickedAdditionalInfo = 0
                    }
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
            padding = padding,
            favoritesViewModel = favoritesViewModel,
            events = events,
            clickedAdditionalInfo = clickedAdditionalInfo,
            onAdditionalInfoClick =  {
                clickedAdditionalInfo = it
            }
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    padding: PaddingValues,
    favoritesViewModel: FavoritesViewModel,
    events: EventsClass,
    clickedAdditionalInfo: Int,
    onAdditionalInfoClick: (Int) -> Unit = {},
) {
    CircularIndeterminatorProgressBar(isDisplayed = events.eventList.isEmpty())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
    ) {
        Button(
            modifier = Modifier
                .filterButton(),
            border = if (clickedAdditionalInfo == 1) {
                BorderStroke(1.dp, Color.DarkGray)
            } else {
                BorderStroke(1.dp, Color.Black)
            },
            shape = RoundedCornerShape(66),
            onClick = {
                if(clickedAdditionalInfo != 1) {
                    onAdditionalInfoClick(1)
                    events.eventList = events.filteredEventList
                }
            }
        ) {
            Text(
                text = "Alle Events",
                color = checkIfLightModeText(),
            )
        }
        Button(
            modifier = Modifier
                .filterButton()
                .padding(start = 4.dp),
            border = if (clickedAdditionalInfo == 2) {
                BorderStroke(1.dp, Color.DarkGray)
            } else {
                BorderStroke(1.dp, Color.Black)
            },
            shape = RoundedCornerShape(44),
            onClick = {
                if(clickedAdditionalInfo != 2) {
                    onAdditionalInfoClick(2)
                    events.eventList = filterEventsToday(filteredEventList = events.filteredEventList)
                }
            }
        ) {
            Text(
                text = "Ab Heute",
                color = checkIfLightModeText(),
            )
        }
        Button(
            modifier = Modifier
                .filterButton(),
            border = if (clickedAdditionalInfo == 3) {
                BorderStroke(1.dp, Color.DarkGray)
            } else {
                BorderStroke(1.dp, Color.Black)
            },
            shape = RoundedCornerShape(44),
            onClick = {
                if(clickedAdditionalInfo != 3) {
                    onAdditionalInfoClick(3)
                    events.eventList =
                        filterEventsSeveralDays(filteredEventList = events.filteredEventList)
                }
            }
        ) {
            Text(
                text = "Mehrtägig",
                color = checkIfLightModeText(),
            )
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                PaddingValues(
                    5.dp,
                    8.dp + 40.dp,
                    padding.calculateTopPadding(),
                    padding.calculateBottomPadding()
                )
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items = events.eventList) { event ->
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


@Composable
fun DropdownMenu(
    events: EventsClass,
    eventsViewModel: EventsViewModel,
    filteredState: FilteredState,
    onFilterClicked: () -> Unit = {},
    ) {
    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.padding(4.dp)
        ) {
            Row {
                Text(
                    color = checkIfLightModeText(),
                    text = when (filteredState.appliedFilter) {
                        1 -> "Attraktionen"
                        2 -> "Kultur"
                        3 -> "Party"
                        else -> "Home"
                    },
                    style = MaterialTheme.typography.button,
                )
                Icon(
                    tint = checkIfLightModeText(),
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Arrow"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 0) {
                    Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show()
                    filteredState.appliedFilter = 0
                    onFilterClicked()
                    events.filteredEventList = eventsViewModel.fetchAllEventsNew()
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }
            }) {
                Text("Home")
            }
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 1) {
                    Toast.makeText(context, "Attraktionen", Toast.LENGTH_SHORT).show()
                    filteredState.appliedFilter = 1
                    onFilterClicked()
                    events.filteredEventList = eventsViewModel.filterEventsByCategory(
                        categoryId = "73",
                        subCategory = "90,+64,+91,+73"
                    )
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }
            }) {
                Text("Attraktionen")
            }
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 2) {
                    Toast.makeText(context, "Kultur & Freizeit", Toast.LENGTH_SHORT).show()
                    filteredState.appliedFilter = 2
                    onFilterClicked()
                    events.filteredEventList =
                        eventsViewModel.filterEventsByCategory(
                            categoryId = "68",
                            subCategory = "68"
                        )
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }

            }) {
                Text("Kultur & Freizeit")
            }
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 3) {
                    Toast.makeText(context, "Party", Toast.LENGTH_SHORT).show()
                    filteredState.appliedFilter = 3
                    onFilterClicked()
                    events.filteredEventList =
                        eventsViewModel.filterEventsByCategory(categoryId = "6", subCategory = "")
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }
            }) {
                Text("Party")
            }
        }
    }
}

data class EventsClass(
    var eventList: List<Event>,
    var filteredEventList: List<Event>,
)

fun Modifier.filterButton(): Modifier =
    height(40.dp)
        .width(130.dp)
        .padding(end = 4.dp)

fun filterEventsToday(filteredEventList: List<Event>): List<Event> {
    val copyEventList = filteredEventList
    val newEventList = copyEventList.filter { event ->
        event.endTime.isEmpty()
    }
    return newEventList
}

fun filterEventsSeveralDays(filteredEventList: List<Event>): List<Event> {
    val copyEventList = filteredEventList
    val newEventList = copyEventList.filter { event ->
        event.endTime.isNotEmpty()
    }
    return newEventList
}