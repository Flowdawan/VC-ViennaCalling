package com.example.viennacalling.screens.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.widgets.checkIfLightModeIcon
import com.example.viennacalling.widgets.checkIfLightModeText
import kotlin.math.log


@Composable
fun RegistrationScreen(
    navController: NavController = rememberNavController(),
    loginViewModel: LoginViewModel) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = { BottomNavigationBar(navController = navController, loginViewModel = loginViewModel) },
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
                            tint = checkIfLightModeText(),
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) {
        MainContent(navController = navController, loginViewModel = loginViewModel)
    }
}

@Composable
fun MainContent(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 24.dp)
            .fillMaxSize(),
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
            onRegistrationClick = { loginViewModel.createUserWithEmailAndPassword(navController = navController)}
        )
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
        label = { Text(text = stringResource(R.string.email)) },
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
        label = { Text(text = stringResource(R.string.password)) },
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
        enabled = enabled,
        content = { Text(text = stringResource(R.string.login)) },
        onClick = {
            onRegistrationClick()
        }
    )
}

@Composable
fun ButtonEmailPasswordCreate(enabled: Boolean = true, onRegistrationClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        content = { Text(text = stringResource(R.string.create)) },
        onClick = { onRegistrationClick() }
    )
}

@Composable
fun RegistrationButton(onRegistrationClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        content = { Text(text = stringResource(R.string.create)) },
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