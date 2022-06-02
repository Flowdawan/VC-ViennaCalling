package com.example.viennacalling.screens.login

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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun LoginScreen(
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
                    contentScale = ContentScale.Crop
                )
            },
                backgroundColor = MaterialTheme.colors.secondary,
                actions = {
                    IconButton(onClick = {
                        navController.navigate(route = AppScreens.FavoriteScreen.name)
                    }) {
                        Icon(
                            tint = White,
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
    padding: PaddingValues,
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

        ButtonEmailPasswordLogin(enabled = loginViewModel.isValidEmailAndPassword()) {
            loginViewModel.signInWithEmailAndPassword(navController = navController)
        }

        RegistrationButton {
            navController.navigate(route = AppScreens.RegistrationScreen.name)
        }

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
fun EmailField(userEmail: String, onEmailChange: (String) -> Unit = {}) {
    val userEmail = userEmail
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = userEmail,
        textStyle = TextStyle(color = checkIfLightModeText()),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = checkIfLightModeText(),
            unfocusedBorderColor = checkIfLightModeText()
        ),
        label = { Text(text = stringResource(R.string.email), color = checkIfLightModeText()) },
        onValueChange = { value ->
            onEmailChange(value)
        }
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit = {}) {
    val password = password
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        value = password,
        textStyle = TextStyle(color = checkIfLightModeText()),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = checkIfLightModeText(),
            unfocusedBorderColor = checkIfLightModeText()
        ),
        label = { Text(text = stringResource(R.string.password), color = checkIfLightModeText()) },
        onValueChange = { value ->
            onPasswordChange(value)
        }
    )
}

@Composable
fun ButtonEmailPasswordLogin(enabled: Boolean = true, onRegistrationClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        enabled = enabled,
        content = { Text(text = stringResource(R.string.login), color = checkIfLightModeText()) },
        onClick = {
            onRegistrationClick()
        }
    )
}

@Composable
fun RegistrationButton(onRegistrationClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        content = { Text(text = stringResource(R.string.create), color = checkIfLightModeText()) },
        onClick = { onRegistrationClick() }
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