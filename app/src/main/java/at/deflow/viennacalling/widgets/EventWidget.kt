package at.deflow.viennacalling.widgets

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import at.deflow.viennacalling.R
import at.deflow.viennacalling.models.Event
import coil.compose.AsyncImage
import coil.request.ImageRequest


private const val TAG = "EventWidget"

@Composable
fun EventRow(
    event: Event,
    onItemClick: (String) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(event.id) },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 6.dp,
    ) {
        Column {
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
                    contentDescription = "Event Cover",
                    contentScale = ContentScale.Crop,
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    color = checkIfLightModeText(),

                    text = event.title,
                    style = MaterialTheme.typography.caption,
                )

                Text(
                    color = checkIfLightModeText(),
                    text = if (event.startTime != "" && event.endTime != "" && (event.startTime != event.endTime)) {
                        "Datum: ${event.startTime} bis ${event.endTime}"
                    } else if (event.startTime != "") {
                        "Datum: ${event.startTime}"
                    } else {
                        "Datum: Es liegen keine Daten zum Datum vor"
                    },
                    style = MaterialTheme.typography.subtitle1,
                )

                if (event.startHour != "" && event.startMin != "") {
                    Text(
                        color = checkIfLightModeText(),
                        text = "Zeit: ${event.startHour}:${event.startMin}",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }


                Text(
                    color = checkIfLightModeText(),
                    text = if (event.streetAddress.trim() != "") {
                        "Ort: ${event.streetAddress} ${event.plz}"
                    } else {
                        "Ort: Es ist leider keine Adresse vorhanden"
                    },
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
            ) {
                content()
            }
        }
    }
}

@Composable
fun FavoriteButton(
    event: Event,
    onFavoriteClick: (Event) -> Unit = {},
    isAlreadyInListColor: Color = Color.DarkGray,
) {

    Button(
        modifier = Modifier
            .padding(10.dp, bottom = 15.dp)
            .fillMaxHeight(),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(44),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = isAlreadyInListColor
        ),
        onClick = { onFavoriteClick(event) }
    ) {
        Text(
            text = if (isAlreadyInListColor == Color.DarkGray) {
                "Merken"
            } else {
                "Gemerkt"
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(6.dp)
        )
    }
}


@Composable
fun EventDetails(
    event: Event,
    content: @Composable () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current // To open external links
    val context = LocalContext.current

    // val gmmIntentUri = Uri.parse("geo:${event.point.replace(" ", ",")}")

    val uri =
        Uri.parse("https://www.google.com/maps/search/?api=1&query=${event.streetAddress} ${event.plz}")

    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
    val mapIntent = Intent(Intent.ACTION_VIEW, uri)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .background(color = MaterialTheme.colors.onBackground)
            .padding(15.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.caption,
                color = checkIfLightModeText()
            )

            Divider(
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )

            Text(
                color = checkIfLightModeText(),
                text = event.description,
                style = MaterialTheme.typography.subtitle1,
            )

            if (event.startTime != "") {
                Divider(
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .alpha(alpha = 0.6F)
                )

                Text(
                    color = checkIfLightModeText(),
                    text = if (event.endTime != "" && (event.startTime != event.endTime)) {
                        "Datum: ${event.startTime} bis ${event.endTime}"
                    } else {
                        "Datum: ${event.startTime}"
                    },
                    style = MaterialTheme.typography.subtitle1,
                )
            }

            if (event.startHour != "" && event.startMin != "") {
                Divider(
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier
                        .padding(10.dp)
                        .alpha(alpha = 0.6F)
                )

                Text(
                    color = checkIfLightModeText(),
                    text = "Zeit: ${event.startHour}:${event.startMin}",
                    style = MaterialTheme.typography.subtitle1,
                )
            }

            Divider(
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )

            Text(
                modifier = Modifier.clickable {
                    if (event.streetAddress != "" && event.point != "") {
                        // Attempt to start an activity that can handle the Intent
                        startActivity(context, mapIntent, null)
                    }
                },
                color = checkIfLightModeText(),
                text = if (event.streetAddress.trim() != "") {
                    "Ort: ${event.streetAddress} ${event.plz}"
                } else {
                    "Ort: Es ist leider keine Adresse vorhanden"
                },
                style = MaterialTheme.typography.subtitle1,
            )

            Divider(
                color = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(10.dp)
                    .alpha(alpha = 0.6F)
            )

            Text(
                modifier = Modifier.clickable {
                    if (event.url != "") {
                        uriHandler.openUri(event.url)
                    } else if (event.link != "") {
                        uriHandler.openUri(event.link)
                    }
                },
                text = if (event.url != "") event.url else event.link,
                style = MaterialTheme.typography.subtitle1,
                color = checkIfLightModeText()
            )

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                content()
            }
        }
    }
}

// Most of the time we want to load dark/white depending on the mode (light/dark) which is defined with the MaterialTheme
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

// We want to load the logo of the app depending on the mode (light/dark) which is defined with the MaterialTheme
@Composable
fun checkIfLightModeIcon(): Int {
    return if (MaterialTheme.colors.isLight) {
        R.drawable.ic_vienna_calling_logo_black
    } else {
        R.drawable.ic_vienna_calling_logo_splash_round
    }
}

@Composable
fun CircularIndeterminatorProgressBar(isDisplayed: Boolean) {
    Log.d(TAG, "Fetching new Events")
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, top = 90.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color.White
            )
        }
    }
}