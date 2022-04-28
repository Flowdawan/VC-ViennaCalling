package com.example.viennacalling.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
import com.example.viennacalling.ui.theme.VcEventCard
import com.example.viennacalling.ui.theme.VcLightGrayPopUp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Preview
@Composable
fun EventRow(
    event: Event = getEvents()[0],
    onItemClick: (String) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .height(351.dp)
            .clickable { onItemClick(event.id) },
        backgroundColor = VcEventCard,
        elevation = 4.dp,
        ) {
        Column() {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(188.dp)
                    .clip(RoundedCornerShape(10.dp))
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
            Column (modifier = Modifier.padding(5.dp)){
                Text(
                    color = Color.White,
                    text = event.title,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    color = Color.White,
                    text = "Datum: ${event.date}",
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    color = Color.White,
                    text = "Ort: ${event.place}",
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Box(contentAlignment = Alignment.TopEnd){
                content()
            }
        }
    }
}

@Composable
fun FavoriteButton(
    event: Event = getEvents()[0],
    onFavoriteClick: (Event) -> Unit = {},
    isAlreadyInList: Boolean = false
) {

    Icon(imageVector = if (!isAlreadyInList) {
        Icons.Default.FavoriteBorder
    } else {
        Icons.Default.Favorite
    },
        contentDescription = "Add favorite Movie",
        modifier = Modifier
            .clickable { onFavoriteClick(event) }
            .padding(15.dp),
        Color.Red
    )
}


@Composable
fun EventDetails(event: Event = getEvents()[0]) {
    Card(
        modifier = Modifier
            .height(375.dp)
            .fillMaxWidth()
            .padding(15.dp)
        ,
        shape = RoundedCornerShape(15.dp)
    ) {
        Column() {
            Text(text = event.title)

            Divider(
                color = VcLightGrayPopUp,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )
            Text(text = event.price)

            Divider(
                color = VcLightGrayPopUp,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )

            Text(text = event.place)

        }


    }
    
}

@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}