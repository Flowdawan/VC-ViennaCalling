package com.example.viennacalling.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.viennacalling.dao.EventsDB
import com.example.viennacalling.repository.EventsRepository
import com.example.viennacalling.screens.eventdetail.EventDetailScreen
import com.example.viennacalling.screens.favorite.FavoriteScreen
import com.example.viennacalling.screens.filter.FilterScreen
import com.example.viennacalling.screens.home.HomeScreen
import com.example.viennacalling.screens.setting.SettingsScreen
import com.example.viennacalling.screens.splash.SplashScreen
import com.example.viennacalling.viewmodels.*


@Composable
fun AppNavigation(themeViewModel: ThemeViewModel = viewModel()) {

    // All our view Models where we save our state of the objects too and pass it to the screens
    // The pattern where the state goes down, and events go up is called a unidirectional data flow
    // We try to use these pattern as good as possible and create as high as possible our states
    val navController = rememberNavController()

    val context = LocalContext.current
    val db = EventsDB.getDatabase(context = context)

    val repository = EventsRepository(eventsDao = db.eventsDao())
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(repository = repository)
    )

    val eventsViewModel: EventsViewModel = viewModel(
        factory = EventsViewModelFactory(repository = repository)
    )

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel,
                eventsViewModel = eventsViewModel
            )
        }
        composable(route = AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(route = AppScreens.FilterScreen.name) {
            FilterScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel,
                eventsViewModel = eventsViewModel
            )
        }
        composable(route = AppScreens.FavoriteScreen.name) {
            FavoriteScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel,
            )
        }
        composable(route = AppScreens.SettingsScreen.name) {
            SettingsScreen(
                navController = navController,
                themeViewModel = themeViewModel
            )
        }

        composable(
            AppScreens.EventDetailScreen.name + "/{eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            EventDetailScreen(
                navController = navController,
                eventId = backStackEntry.arguments?.getString("eventId"),
                favoritesViewModel = favoritesViewModel,
                eventsViewModel = eventsViewModel
            )
        }
    }
}

