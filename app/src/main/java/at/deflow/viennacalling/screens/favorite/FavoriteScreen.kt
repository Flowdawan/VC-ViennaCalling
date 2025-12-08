package at.deflow.viennacalling.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.net.Uri
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.EventRow
import at.deflow.viennacalling.widgets.FavoriteButton
import at.deflow.viennacalling.widgets.checkIfLightModeIcon
import at.deflow.viennacalling.widgets.checkIfLightModeText

@Composable
fun FavoriteScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel,
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(checkIfLightModeIcon()),
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
                actions = {
                    IconButton(onClick = { /* Maybe add search or share later */ }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = checkIfLightModeText()
                        )
                    }
                }
            )
        }
    ) { padding ->
        MainContent(
            navController = navController,
            favoritesViewModel = favoritesViewModel,
            padding = padding
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    padding: PaddingValues
) {
    val eventList: List<Event> by favoritesViewModel.favoriteEvents.collectAsState()
    LazyColumn(
        modifier = Modifier.padding(
            PaddingValues(
                start = 5.dp,
                top = padding.calculateTopPadding() + 40.dp,
                bottom = padding.calculateBottomPadding(),
                end = 5.dp
            )
        ),
    ) {
        items(items = eventList) { event ->
            EventRow(
                event = event,
                onItemClick = { eventId ->
                    val safeId = Uri.encode(eventId.ifBlank { event.title })
                    navController.navigate(route = AppScreens.EventDetailScreen.name + "/$safeId")
                }
            ) {
                FavoriteButton(
                    event = event,
                    isAlreadyInListColor = Purple700,
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
