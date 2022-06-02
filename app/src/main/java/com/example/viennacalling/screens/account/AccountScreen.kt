package com.example.viennacalling.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.screens.setting.Alert
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.checkIfLightModeIcon
import com.example.viennacalling.widgets.checkIfLightModeText

@Composable
fun AccountScreen(
    navController: NavController = rememberNavController(),
    loginViewModel: LoginViewModel
) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                loginViewModel = loginViewModel
            )
        },
        topBar = {
            TopAppBar({
                Image(
                    painterResource(
                        checkIfLightModeIcon()
                    ),
                    contentDescription = "Vienna Calling Logo",
                    contentScale = ContentScale.Crop
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
            loginViewModel = loginViewModel
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    padding: PaddingValues,
    loginViewModel: LoginViewModel
) {
    val showDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(20.dp, padding.calculateBottomPadding()))
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            tint = checkIfLightModeText(),
            contentDescription = "User Image",
            modifier = Modifier
                .size(80.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "User Email",
                tint = checkIfLightModeText(),

                )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = loginViewModel.userEmail.value,
                color = checkIfLightModeText(),
                modifier = Modifier.padding(3.dp)
            )
        }


        LogoutButton(loginViewModel = loginViewModel, navController = navController)

        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.BottomCenter
        ) {
            TextButton(
                onClick = { showDialog.value = true }) {
                Text(text = "Lerne über Vienna Calling", color = checkIfLightModeText())
            }
        }
        if (showDialog.value) {
            Alert(name = stringResource(R.string.about_text),
                showDialog = showDialog.value,
                onDismiss = { showDialog.value = false })
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Composable
fun LogoutButton(loginViewModel: LoginViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { loginViewModel.signOut(navController = navController) },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        ) {
            Text(text = stringResource(R.string.log_out), color = checkIfLightModeText())
        }
    }
}