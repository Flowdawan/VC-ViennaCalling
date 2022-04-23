package com.example.viennacalling.screens.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.ui.theme.VcNavTopBottom
import com.example.viennacalling.ui.theme.VcScreenBackground


@Composable
fun RegistrationScreen(navController: NavController = rememberNavController()) {
    Scaffold(
        backgroundColor = VcScreenBackground,
        bottomBar = { BottomNavigationBar(navController = navController) },
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
    ) {
        MainContent(navController = navController)
    }
}

@Composable
fun MainContent(navController: NavController, events: List<Event> = getEvents()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = {
            signUp()
        }) {
            Text(text = "Register")
        }
    }
}


private fun signUp() {
/*
TODO
// List of Providers defined on Firebase
val provider = arrayListOf(
    AuthUI.IdpConfig.EmailBuilder().build()
)
val signinIntent = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(provider)
    .build()

signInLauncher.launch(signinIntent)
*/

}


/*
// Callback function to get feedback when signing in
private fun signInResult(result: FirebaseAuthUIAuthenticationResult){
val response = result.idpResponse
if(result.resultCode == RESULT_OK){
    user = FirebaseAuth.getInstance().currentUser
} else{
    Log.e("Registration", "Error loggin in" + response?.error?.message)
}
}
*/