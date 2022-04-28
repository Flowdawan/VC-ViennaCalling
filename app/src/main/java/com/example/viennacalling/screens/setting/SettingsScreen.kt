package com.example.viennacalling.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.ui.theme.*
import com.example.viennacalling.viewmodels.LoginViewModel
import kotlin.math.log

@Composable
fun SettingsScreen(navController: NavController = rememberNavController(), loginViewModel: LoginViewModel) {
    Scaffold(
        backgroundColor = VcScreenBackground,
        bottomBar = { BottomNavigationBar(navController = navController, loginViewModel = loginViewModel) },
        topBar = {
            TopAppBar({
                Image(
                    painterResource(R.drawable.ic_vc_logo),
                    contentDescription = "Vienna Calling Logo",
                    contentScale = ContentScale.Crop
                )
            },
                backgroundColor = VcNavTopBottom,
                actions = {
                    IconButton(onClick = {
                        navController.navigate(route = AppScreens.FavoriteScreen.name)
                    }) {
                        Icon(
                            tint = Color.White,
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { padding ->
        MainContent(navController = navController, padding = padding)
    }
}

@Composable
fun MainContent(navController: NavController, events: List<Event> = getEvents(), padding: PaddingValues) {
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
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ,
            colors = ButtonDefaults.buttonColors(backgroundColor = VcButtons),
            onClick = { /*TODO*/ }
        ) {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Night Mode", tint = Color.White)
            Text(text = "Dark mode", color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            onClick = { /*TODO*/ }
        ) {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Night Mode", tint = Color.White)
            Text(text = "Light mode", color = Color.White)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.BottomCenter
        ) {
            TextButton(
                onClick = { showDialog.value = true }){
                Text(text = "Lerne über Vienna Calling", color = Color.White)
            }
        }
        if (showDialog.value) {
            Alert(name = "Vienna Calling ist eine App, die von Wiener EntwicklerInnen mit Unterstützung der FH Campus Wien umgesetzt wurde. Diese App ermöglicht es EinwohnerInnen und TouristInnen, die besten kulturellen und musikalischen Veranstaltungen in dieser Stadt zu finden. Wir möchten Menschen zusammenbringen und jedem die Möglichkeit geben, die Stadt zu erkunden. Es gibt so viele tolle Orte und Veranstaltungen, die man im Sommer besuchen kann. Klicken Sie einfach auf die Schaltfläche \"Interessiert\", damit Sie die Veranstaltung auf Ihrer Liste speichern können. Vergessen Sie auch nicht, ein Konto zu erstellen, um auf alle unsere Funktionen zugreifen zu können. Bleiben Sie dran, wir werden Sie über unsere neuen Funktionen auf dem Laufenden halten. \n",
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
                    Text("Vienna Calling")
                }
            },
            text = {
                Text(text = name)
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            },
            dismissButton = {},
            backgroundColor = VcLightGrayPopUp,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(18.dp)
        )
    }
}
