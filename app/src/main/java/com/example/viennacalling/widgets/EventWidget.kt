package com.example.viennacalling.widgets

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.viennacalling.R
import com.example.viennacalling.models.Event
import com.example.viennacalling.models.getEvents
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
        backgroundColor = MaterialTheme.colors.primary,
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
                        .data(event.images)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie Cover",
                    contentScale = ContentScale.Crop,
                )
            }
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    color = checkIfLightModeText(),
                    text = event.title,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    color = checkIfLightModeText(),
                    text = "Datum: ${event.date}",
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    color = checkIfLightModeText(),
                    text = "Ort: ${event.location}",
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Box(contentAlignment = Alignment.TopEnd) {
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
            .padding(15.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column() {
            Text(text = event.title)

            Divider(
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )
            Text(text = event.price)

            Divider(
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )

            Text(text = event.location)

        }


    }

}

@Composable
fun checkIfLightModeText(reverse: Boolean = false): Color {
    return if (MaterialTheme.colors.isLight) {
        when (reverse) {
            true -> Color.White
            false -> Color.Black
        }
    } else {
        when (reverse) {
            true -> Color.Black
            false -> Color.White
        }
    }
}

@Composable
fun checkIfLightModeIcon(): Int {
    if (MaterialTheme.colors.isLight) {
        return R.drawable.ic_vc_logo_light
    } else {
        return R.drawable.ic_vc_logo
    }
}