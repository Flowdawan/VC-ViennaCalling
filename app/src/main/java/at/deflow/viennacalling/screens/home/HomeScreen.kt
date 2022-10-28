package at.deflow.viennacalling.screens.home

import android.util.Log
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel = viewModel(),
    eventsViewModel: EventsViewModel = viewModel(),
) {
    val filteredState = eventsViewModel.eventsFilterState
    eventsViewModel.cacheNewList()

    var clickedAdditionalInfo by remember {
        mutableStateOf(0)
    }

    var isTimeout by remember {
        mutableStateOf(false)
    }

    val events = EventsClass(
        eventList = eventsViewModel.getAllEvents(),
        filteredEventList = eventsViewModel.getAllEvents(),
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
                    onFilterClicked = {
                        clickedAdditionalInfo = 0
                        isTimeout = false
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
            isTimeout = isTimeout,
            onAdditionalInfoClick = { clickedElement, timeout ->
                clickedAdditionalInfo = clickedElement
                isTimeout = timeout
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
    isTimeout: Boolean,
    onAdditionalInfoClick: (Int, Boolean) -> Unit = { _: Int, _: Boolean -> },
) {
    if (!isTimeout) {
        CircularIndeterminatorProgressBar(isDisplayed = events.eventList.isEmpty())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        horizontalArrangement = Arrangement.Center,
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
                if (clickedAdditionalInfo != 1) {
                    events.eventList = events.filteredEventList
                    onAdditionalInfoClick(1, events.eventList.isEmpty())
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
                if (clickedAdditionalInfo != 2) {
                    events.eventList =
                        filterEventsToday(
                            filteredEventList = events.filteredEventList,
                        )
                    onAdditionalInfoClick(2, events.eventList.isEmpty())
                }
            }
        ) {
            Text(
                text = "Ab heute",
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
                if (clickedAdditionalInfo != 3) {
                    events.eventList =
                        filterEventsJustToday(filteredEventList = events.filteredEventList)
                    onAdditionalInfoClick(3, events.eventList.isEmpty())
                }

            }
        ) {
            Text(
                text = "Heute",
                color = checkIfLightModeText(),
            )
        }
    }
    if (isTimeout) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = 5.dp,
                        top = padding.calculateTopPadding() + 40.dp,
                        bottom = padding.calculateBottomPadding(),
                        end = 5.dp
                    )
                ),
        ) {
            Text(
                text = "Es wurden keine Events gefunden!",
                style = MaterialTheme.typography.caption,
                color = checkIfLightModeText(),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = 5.dp,
                        top = padding.calculateTopPadding() + 54.dp,
                        bottom = padding.calculateBottomPadding(),
                        end = 5.dp
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
}


@Composable
fun DropdownMenu(
    events: EventsClass,
    eventsViewModel: EventsViewModel,
    filteredState: FilteredState,
    onFilterClicked: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

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
                    filteredState.appliedFilter = 0
                    onFilterClicked()
                    events.filteredEventList = eventsViewModel.fetchAllEventsNew()
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }
            }) {
                Text("Home", color = checkIfLightModeText())
            }
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 1) {
                    filteredState.appliedFilter = 1
                    onFilterClicked()
                    events.filteredEventList =
                        eventsViewModel.filterBySightseeing()
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }
            }) {
                Text("Attraktionen", color = checkIfLightModeText())
            }
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 2) {
                    filteredState.appliedFilter = 2
                    onFilterClicked()
                    events.filteredEventList =
                        eventsViewModel.filterByCulture()
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }

            }) {
                Text("Kultur & Freizeit", color = checkIfLightModeText())
            }
            DropdownMenuItem(onClick = {
                if (filteredState.appliedFilter != 3) {
                    filteredState.appliedFilter = 3
                    onFilterClicked()
                    events.filteredEventList =
                        eventsViewModel.filterByParty()
                    events.eventList = events.filteredEventList
                    expanded = !expanded
                }
            }) {
                Text("Party", color = checkIfLightModeText())
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

fun filterEventsToday(
    filteredEventList: List<Event>,
): List<Event> {
    val copyEventList = filteredEventList

    val currentDate = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formattedDate = currentDate.format(formatter)

    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
    val firstDate: Date = sdf.parse(formattedDate) as Date

    val newEventList = copyEventList.filter { event ->
        val secondDate: Date = sdf.parse(event.startTime) as Date
        secondDate.after(firstDate) || secondDate.equals(firstDate)
    }

    return newEventList
}

fun filterEventsJustToday(
    filteredEventList: List<Event>,
): List<Event> {
    val copyEventList = filteredEventList

    val currentDate = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val formattedDate = currentDate.format(formatter)

    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
    val firstDate: Date = sdf.parse(formattedDate) as Date

    val newEventList = copyEventList.filter { event ->
        val secondDate: Date = sdf.parse(event.startTime) as Date
        secondDate.equals(firstDate)
    }

    return newEventList
}