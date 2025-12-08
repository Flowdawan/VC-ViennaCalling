package at.deflow.viennacalling.screens.eventdetail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.core.content.ContextCompat.startActivity
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.CircularIndeterminatorProgressBar
import at.deflow.viennacalling.widgets.checkIfLightModeText
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EventDetailScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    eventsViewModel: EventsViewModel,
    eventId: String? = null,
) {
    val uiState by eventsViewModel.uiState.collectAsState()
    val decodedEventId = remember(eventId) { eventId?.let { Uri.decode(it) } }
    val event = remember(decodedEventId, uiState.events) {
        filterEvent(decodedEventId, uiState.events)
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
    ) { padding ->
        if (uiState.isLoading) {
            CircularIndeterminatorProgressBar(isDisplayed = true)
        } else if (event == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Event konnte nicht geladen werden.",
                    color = checkIfLightModeText()
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(event.images)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Event Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )

                // Content
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Spacer for image
                    item {
                        Spacer(modifier = Modifier.height(280.dp))
                    }

                    // Event content
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colors.background,
                                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                                )
                                .padding(24.dp)
                        ) {
                            Text(
                                text = event.title,
                                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            EventInfoPill(event.category)
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "About",
                                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = event.description,
                                style = MaterialTheme.typography.body1,
                                lineHeight = 24.sp
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Location & Time",
                                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(text = if (event.streetAddress.isNotBlank()) "${event.streetAddress}, ${event.plz}" else "Adresse: k. A.")
                            Text(text = when {
                                event.startTime.isNotBlank() && event.endTime.isNotBlank() -> "${event.startTime} - ${event.endTime}"
                                event.startTime.isNotBlank() -> event.startTime
                                else -> "Datum: k. A."
                            })
                            Spacer(modifier = Modifier.height(16.dp))
                            ActionButtons(event = event)
                        }
                    }
                }

                // Top Bar with back and favorite
                TopBar(navController = navController, favoritesViewModel = favoritesViewModel, event = event)
            }
        }
    }
}

@Composable
private fun TopBar(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    event: Event
) {
    var isFavorite by remember(event) { mutableStateOf(favoritesViewModel.isEventInList(event)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        IconButton(
            onClick = {
                if (isFavorite) {
                    favoritesViewModel.removeEvent(event)
                } else {
                    favoritesViewModel.addEvent(event)
                }
                isFavorite = !isFavorite
            },
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(
                if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) Purple700 else Color.White
            )
        }
    }
}

@Composable
fun EventInfoPill(text: String) {
    if (text.isNotBlank()) {
        Surface(
            color = MaterialTheme.colors.secondary.copy(alpha = 0.5f),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = text.uppercase(),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}

fun filterEvent(eventId: String?, eventList: List<Event>): Event? {
    return eventList.firstOrNull { event -> event.id == eventId }
}

@Composable
private fun ActionButtons(event: Event) {
    val context = LocalContext.current
    val shareText = buildString {
        append(event.title)
        if (event.url.isNotBlank()) append("\n${event.url}") else if (event.link.isNotBlank()) append("\n${event.link}")
    }
    val mapQuery = listOf(event.streetAddress, event.plz).filter { it.isNotBlank() }.joinToString(" ")
    val mapUri = "https://www.google.com/maps/search/?api=1&query=${Uri.encode(mapQuery)}"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                runCatching { startActivity(context, Intent.createChooser(shareIntent, "Event teilen"), null) }
            }
        ) {
            Icon(Icons.Default.Share, contentDescription = "Teilen", tint = MaterialTheme.colors.onBackground)
        }

        IconButton(
            onClick = {
                if (mapQuery.isNotBlank()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUri))
                    runCatching { startActivity(context, intent, null) }
                }
            }
        ) {
            Icon(Icons.Default.Map, contentDescription = "Karte öffnen", tint = MaterialTheme.colors.onBackground)
        }

        IconButton(
            onClick = {
                val target = when {
                    event.url.isNotBlank() -> event.url
                    event.link.isNotBlank() -> event.link
                    else -> ""
                }
                if (target.isNotBlank()) {
                    runCatching { startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(target)), null) }
                }
            }
        ) {
            Icon(Icons.Default.Public, contentDescription = "Website öffnen", tint = MaterialTheme.colors.onBackground)
        }
    }
}
