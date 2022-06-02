package com.example.viennacalling.navigation.bottomnav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.checkIfLightModeText

@Composable
fun BottomNavigationBar(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Filter,
        BottomNavItem.Account,
        BottomNavItem.Settings,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = checkIfLightModeText()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = (item.icon), contentDescription = item.title) },
                selectedContentColor = checkIfLightModeText(),
                unselectedContentColor = checkIfLightModeText().copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    if (!loginViewModel.isLoggedIn.value && item.screen_route == "AccountScreen") {
                        item.screen_route = AppScreens.LoginScreen.name
                    } else if (loginViewModel.isLoggedIn.value && item.screen_route == "LoginScreen") {
                        item.screen_route = AppScreens.AccountScreen.name
                    }
                    navController.navigate(item.screen_route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true

                        // Restore state when reselecting a previously selected item
                        // restoreState = true
                    }
                }
            )
        }
    }
}