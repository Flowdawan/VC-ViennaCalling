package at.deflow.viennacalling.screens.filter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.checkIfLightModeText

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

            },
                backgroundColor = MaterialTheme.colors.background,
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
    Text("SearchBar")
}