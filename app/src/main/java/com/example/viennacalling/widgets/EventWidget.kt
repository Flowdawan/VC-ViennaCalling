package com.example.viennacalling.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

@Preview
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EventRow(
    event: Event = getEvents()[0],
    onItemClick: (String) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    var isArrowUp by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(4.dp)
            .clickable { onItemClick(event.id) },
        elevation = 6.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(110.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(event.images[0])
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
            }
            Column {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = "Date: ${event.date}",
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    text = "Place: ${event.place}",
                    style = MaterialTheme.typography.subtitle1,
                )
                AnimatedVisibility(visible = isArrowUp) {
                    Column(
                        modifier = Modifier.padding(7.dp)
                            .width(200.dp),
                    ) {
                        Text(
                            text = "Plot: ${event.description}",
                            style = MaterialTheme.typography.subtitle2,
                        )
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(2.dp)
                                .alpha(alpha = 0.6F)
                        )
                        Text(
                            text = "Genre: ${event.type}",
                            style = MaterialTheme.typography.subtitle2,
                        )
                    }
                }
                Icon(imageVector = if (isArrowUp) {
                    Icons.Default.KeyboardArrowDown
                } else {
                    Icons.Default.KeyboardArrowUp
                },
                    contentDescription = "Arrow",
                    modifier = Modifier.clickable { isArrowUp = !isArrowUp }
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