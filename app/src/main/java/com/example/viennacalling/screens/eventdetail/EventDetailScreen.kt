package com.example.viennacalling.screens.eventdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.navigation.AppScreens
import com.example.viennacalling.navigation.bottomnav.BottomNavigationBar
import com.example.viennacalling.ui.theme.VcLightGrayPopUp
import com.example.viennacalling.ui.theme.VcNavTopBottom
import com.example.viennacalling.ui.theme.VcScreenBackground
import com.example.viennacalling.viewmodels.FavoritesViewModel
import com.example.viennacalling.widgets.EventDetails
import com.example.viennacalling.widgets.EventRow
import com.example.viennacalling.widgets.FavoriteButton


@Preview(showBackground = true)
@Composable
fun EventDetailScreen(
    navController: NavController = rememberNavController(),
    eventId: String? = getEvents()[0].id,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val event = filterMovie(eventId)
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
        MainContent(event, favoritesViewModel = favoritesViewModel)
    }

}

@Composable
fun MainContent(event: Event, favoritesViewModel: FavoritesViewModel = viewModel()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(event.images[0])
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie Cover",
                    contentScale = ContentScale.Crop,
                )
            }
            Divider(
                color = VcLightGrayPopUp,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )
            EventDetails(event = event)
        }
    }
}
fun filterMovie(eventId: String?): Event {
    return getEvents().filter { event -> event.id == eventId }[0]
}
