package at.deflow.viennacalling.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import android.net.Uri
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsUiState
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.CircularIndeterminatorProgressBar
import at.deflow.viennacalling.widgets.EventRow
import at.deflow.viennacalling.widgets.FavoriteButton
import at.deflow.viennacalling.widgets.checkIfLightModeText

@Composable
fun HomeScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    eventsViewModel: EventsViewModel = viewModel(),
) {
    val uiState by eventsViewModel.uiState.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    FilterDropdownMenu(
                        uiState = uiState,
                        eventsViewModel = eventsViewModel,
                    )
                },
                backgroundColor = MaterialTheme.colors.secondary,
                actions = {
                    IconButton(onClick = { eventsViewModel.retry() }) {
                        Icon(
                            tint = checkIfLightModeText(),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh events"
                        )
                    }
                    IconButton(onClick = { navController.navigate(route = AppScreens.FavoriteScreen.name) }) {
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
            eventsViewModel = eventsViewModel,
            uiState = uiState
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    padding: PaddingValues,
    favoritesViewModel: FavoritesViewModel,
    eventsViewModel: EventsViewModel,
    uiState: EventsUiState,
) {
    CircularIndeterminatorProgressBar(isDisplayed = uiState.isLoading)

    if (uiState.errorMessage != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = uiState.errorMessage,
                    style = MaterialTheme.typography.caption,
                    color = checkIfLightModeText(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { eventsViewModel.retry() }) {
                    Text(text = "Erneut versuchen")
                }
            }
        }
    } else if (!uiState.isLoading && uiState.events.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Es wurden keine Events gefunden!",
                style = MaterialTheme.typography.caption,
                color = checkIfLightModeText(),
            )
        }
    } else {
        Column {
            FilterButtons(eventsViewModel = eventsViewModel, uiState = uiState)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        PaddingValues(
                            start = 5.dp,
                            top = 10.dp,
                            bottom = padding.calculateBottomPadding(),
                            end = 5.dp
                        )
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(items = uiState.events) { event ->
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
                            val safeId = Uri.encode(eventId.ifBlank { event.title })
                            navController.navigate(route = AppScreens.EventDetailScreen.name + "/$safeId")
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
}

@Composable
private fun FilterButtons(
    eventsViewModel: EventsViewModel,
    uiState: EventsUiState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.padding(end = 4.dp),
            border = if (uiState.dateFilter == 0) BorderStroke(1.dp, Color.DarkGray) else BorderStroke(
                1.dp,
                Color.Black
            ),
            shape = RoundedCornerShape(66),
            onClick = { eventsViewModel.setDateFilter(0) }
        ) {
            Text(
                text = "Alle Events",
                color = checkIfLightModeText(),
            )
        }
        Button(
            modifier = Modifier.padding(start = 4.dp),
            border = if (uiState.dateFilter == 1) BorderStroke(1.dp, Color.DarkGray) else BorderStroke(
                1.dp,
                Color.Black
            ),
            shape = RoundedCornerShape(44),
            onClick = { eventsViewModel.setDateFilter(1) }
        ) {
            Text(
                text = "Ab heute",
                color = checkIfLightModeText(),
            )
        }
        Button(
            modifier = Modifier.padding(start = 4.dp),
            border = if (uiState.dateFilter == 2) BorderStroke(1.dp, Color.DarkGray) else BorderStroke(
                1.dp,
                Color.Black
            ),
            shape = RoundedCornerShape(44),
            onClick = { eventsViewModel.setDateFilter(2) }
        ) {
            Text(
                text = "Heute",
                color = checkIfLightModeText(),
            )
        }
    }
}


@Composable
fun FilterDropdownMenu(
    eventsViewModel: EventsViewModel,
    uiState: EventsUiState,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier.wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(
            onClick = { expanded = true },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    color = checkIfLightModeText(),
                    text = when (uiState.categoryFilter) {
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
                eventsViewModel.setCategoryFilter(0)
                expanded = false
            }) {
                Text("Home", color = checkIfLightModeText())
            }
            DropdownMenuItem(onClick = {
                eventsViewModel.setCategoryFilter(1)
                expanded = false
            }) {
                Text("Attraktionen", color = checkIfLightModeText())
            }
            DropdownMenuItem(onClick = {
                eventsViewModel.setCategoryFilter(2)
                expanded = false

            }) {
                Text("Kultur & Freizeit", color = checkIfLightModeText())
            }
            DropdownMenuItem(onClick = {
                eventsViewModel.setCategoryFilter(3)
                expanded = false
            }) {
                Text("Party", color = checkIfLightModeText())
            }
        }
    }
}
