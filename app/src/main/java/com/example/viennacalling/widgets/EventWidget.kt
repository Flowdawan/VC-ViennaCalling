package com.example.viennacalling.widgets

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            .width(323.dp)
            .height(277.dp)
            .clickable { onItemClick(event.id) },
        backgroundColor = VcEventCard,
        elevation = 6.dp,
        ) {
        Column(
            modifier = Modifier.padding(7.dp)
        ) {
            Surface(
                modifier = Modifier
                    .width(309.dp)
                    .height(188.dp)
                    .padding(2.dp)
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
            Column {
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
            content()
        }
    }
}

@Composable
fun FavoriteIcon(
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
fun HorizontalScrollableImageView(event: Event = getEvents()[0]) {
    LazyRow {
        items(event.images) { movie ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 4.dp
            )
            {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RectangleShape)
                )
            }
        }
    }
}