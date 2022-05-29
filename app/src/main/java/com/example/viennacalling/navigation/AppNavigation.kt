package com.example.viennacalling.navigation

import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.viennacalling.dao.EventsDB
import com.example.viennacalling.models.EventListResponse
import com.example.viennacalling.repository.EventsRepository
import com.example.viennacalling.retrofit.RetrofitInstance
import com.example.viennacalling.screens.account.AccountScreen
import com.example.viennacalling.screens.eventdetail.EventDetailScreen
import com.example.viennacalling.screens.favorite.FavoriteScreen
import com.example.viennacalling.screens.filter.FilterScreen
import com.example.viennacalling.screens.home.HomeScreen
import com.example.viennacalling.screens.login.LoginScreen
import com.example.viennacalling.screens.registration.RegistrationScreen
import com.example.viennacalling.screens.setting.SettingsScreen
import com.example.viennacalling.screens.splash.SplashScreen
import com.example.viennacalling.viewmodels.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


@Composable
fun AppNavigation(themeViewModel: ThemeViewModel = viewModel()) {

    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val eventsViewModel: EventsViewModel = viewModel()


    val context = LocalContext.current
    val db = EventsDB.getDatabase(context = context)
    val repository = EventsRepository(eventsDao = db.eventsDao())
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(repository = repository)
    )

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {
        composable(AppScreens.HomeScreen.name) {
            HomeScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel,
                loginViewModel = loginViewModel,
                eventsViewModel = eventsViewModel
            )
        }
        composable(route = AppScreens.SplashScreen.name) { SplashScreen(navController = navController) }
        composable(route = AppScreens.LoginScreen.name) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(route = AppScreens.RegistrationScreen.name) {
            RegistrationScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(route = AppScreens.FilterScreen.name) {
            FilterScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(route = AppScreens.FavoriteScreen.name) {
            FavoriteScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel,
                loginViewModel = loginViewModel
            )
        }
        composable(route = AppScreens.SettingsScreen.name) {
            SettingsScreen(
                navController = navController,
                loginViewModel = loginViewModel,
                themeViewModel = themeViewModel
            )
        }
        composable(route = AppScreens.AccountScreen.name) {
            AccountScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }
        composable(
            AppScreens.EventDetailScreen.name + "/{eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("eventId")
                ?.let {
                    EventDetailScreen(
                        navController = navController,
                        eventId = it,
                        favoritesViewModel = favoritesViewModel,
                        loginViewModel = loginViewModel
                    )
                }
        }
    }
}

