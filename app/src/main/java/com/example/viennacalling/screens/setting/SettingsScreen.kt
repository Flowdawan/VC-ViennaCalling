package com.example.viennacalling.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.viewmodels.ThemeViewModel
import com.example.viennacalling.widgets.checkIfLightModeIcon
import com.example.viennacalling.widgets.checkIfLightModeText

@Composable
fun SettingsScreen(navController: NavController = rememberNavController(),
                   loginViewModel: LoginViewModel,
                    themeViewModel: ThemeViewModel,
                    ) {
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
    ) { padding ->
        MainContent(
            navController = navController,
            padding = padding,
            themeViewModel = themeViewModel,
            )
    }
}

@Composable
fun MainContent(navController: NavController,
                padding: PaddingValues,
                themeViewModel: ThemeViewModel,
) {
    val showDialog = remember { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(20.dp, padding.calculateBottomPadding()))
            ){
        Text(
            text = "Settings",
            fontSize = 32.sp,
            color = checkIfLightModeText(),
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            onClick = {
                themeViewModel.onThemeChanged("Dark")
            }
        ) {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Night Mode", tint = checkIfLightModeText())
            Text(text = "Night mode",
                color = checkIfLightModeText(),
                modifier = Modifier.padding(3.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            onClick = {
                themeViewModel.onThemeChanged("Light")
            }
        ) {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Light Mode", tint = checkIfLightModeText())
            Text(text = "Light mode",
                color = checkIfLightModeText(),
                modifier = Modifier.padding(3.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.BottomCenter
        ) {
            TextButton(
                onClick = { showDialog.value = true }){
                Text(text = "Lerne über Vienna Calling", color = checkIfLightModeText())
            }
        }
        if (showDialog.value) {
            Alert(name = stringResource(R.string.about_text),
                showDialog = showDialog.value,
                onDismiss = {showDialog.value = false})
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun Alert(name : String,
          showDialog: Boolean,
          onDismiss: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            title = {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Mehr über Vienna Calling")
                    Text("Vienna Calling", color = checkIfLightModeText())
                }
            },
            text = {
                Text(text = name, color = checkIfLightModeText())
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK", color = checkIfLightModeText())
                }
            },
            dismissButton = {},
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(18.dp)
        )
    }
}
