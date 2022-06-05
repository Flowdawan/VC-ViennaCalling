package com.example.viennacalling.screens.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.screens.login.EmailField
import com.example.viennacalling.screens.login.PasswordField
import com.example.viennacalling.screens.setting.Alert
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.checkIfLightModeIcon
import com.example.viennacalling.widgets.checkIfLightModeText


@Composable
fun RegistrationScreen(
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
                    painterResource(checkIfLightModeIcon()),
                    contentDescription = "Vienna Calling Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(133.dp)
                        .height(47.dp)
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
    loginViewModel: LoginViewModel,
    padding: PaddingValues
) {
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(PaddingValues(20.dp, padding.calculateBottomPadding()))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (loginViewModel.error.value.isNotBlank()) {
            ErrorField(error = loginViewModel.error.value)
        }

        EmailField(userEmail = loginViewModel.userEmail.value) { value ->
            loginViewModel.setUserEmail(value)
        }

        PasswordField(password = loginViewModel.password.value) { value ->
            loginViewModel.setPassword(value)
        }

        ButtonEmailPasswordCreate(
            enabled = loginViewModel.isValidEmailAndPassword(),
            onRegistrationClick = { loginViewModel.createUserWithEmailAndPassword(navController = navController) }
        )
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
fun ButtonEmailPasswordCreate(enabled: Boolean = true, onRegistrationClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        enabled = enabled,
        content = { Text(text = "Registrieren", color = checkIfLightModeText()) },
        onClick = {
            onRegistrationClick()
        }
    )
}

@Composable
fun ErrorField(error: String) {
    Text(
        text = error,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Red,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
}