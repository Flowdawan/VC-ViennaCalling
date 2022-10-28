package at.deflow.viennacalling.screens.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.ui.theme.Purple700
import at.deflow.viennacalling.viewmodels.EventsViewModel
import at.deflow.viennacalling.viewmodels.FavoritesViewModel
import at.deflow.viennacalling.widgets.*

private const val TAG = "FilterScreen"

@Composable
fun FilterScreen(
    navController: NavController = rememberNavController(),
    favoritesViewModel: FavoritesViewModel,
    eventsViewModel: EventsViewModel
) {

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        },
        topBar = {
            TopAppBar({
                Image(
                    painterResource(checkIfLightModeIcon()),
                    contentDescription = "Vienna Calling Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(133.dp)
                        .height(57.dp)
                        .clickable {
                            navController.navigate(route = AppScreens.HomeScreen.name)
                        }
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
            favoritesViewModel = favoritesViewModel,
            padding = padding,
            eventsViewModel = eventsViewModel,
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    padding: PaddingValues,
    eventsViewModel: EventsViewModel
) {
    val eventList = eventsViewModel.getEventListForSearch()
    val initialEventList = eventList.toList()
    var searchedList: List<Event>

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    CircularIndeterminatorProgressBar(isDisplayed = initialEventList.isEmpty())

    SearchBar(state = textState)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                PaddingValues(
                    start = 5.dp,
                    top = padding.calculateTopPadding() + 65.dp,
                    bottom = padding.calculateBottomPadding(),
                    end = 5.dp
                )
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val searchedText = textState.value.text
        searchedList = if (searchedText.isEmpty()) {
            initialEventList
        } else {
            eventList.filter { event ->
                event.title.contains(searchedText.trim(), ignoreCase = true) ||
                        event.streetAddress.contains(searchedText.trim(), ignoreCase = true)
            }
        }
        items(items = searchedList) { event ->
            var isInListColor by remember {
                if (favoritesViewModel.isEventInList(event)) {
                    mutableStateOf(Purple700)
                } else {
                    mutableStateOf(Color.DarkGray)
                }
            }
            EventRow(
                event = event,
                onItemClick = { eventId ->
                    navController.navigate(route = AppScreens.EventDetailScreen.name + "/$eventId")
                }) {
                FavoriteButton(
                    event = event,
                    isAlreadyInListColor = isInListColor,
                    onFavoriteClick = { event ->
                        if (favoritesViewModel.isEventInList(event)) {
                            favoritesViewModel.removeEvent(event)
                            isInListColor = Color.DarkGray
                        } else {
                            favoritesViewModel.addEvent(event)
                            isInListColor = Purple700
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SearchBar(state: MutableState<TextFieldValue>) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        placeholder = { Text("Search events...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, end = 5.dp, start = 5.dp)
            .shadow(4.dp, CircleShape),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Searchbar Icon",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Closing Searchbar",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        shape = RoundedCornerShape(20.dp), // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
