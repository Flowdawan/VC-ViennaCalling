package at.deflow.viennacalling.screens.setting

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConnectWithoutContact
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.deflow.viennacalling.R
import at.deflow.viennacalling.navigation.AppScreens
import at.deflow.viennacalling.navigation.bottomnav.BottomNavigationBar
import at.deflow.viennacalling.viewmodels.ThemeViewModel
import at.deflow.viennacalling.widgets.checkIfLightModeIcon
import at.deflow.viennacalling.widgets.checkIfLightModeText

private const val TAG = "SettingsScreen"

@Composable
fun SettingsScreen(
    navController: NavController = rememberNavController(),
    themeViewModel: ThemeViewModel,
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
            padding = padding,
            themeViewModel = themeViewModel,
        )
    }
}

@Composable
fun MainContent(
    navController: NavController,
    padding: PaddingValues,
    themeViewModel: ThemeViewModel,
) {
    val showDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(20.dp, padding.calculateBottomPadding()))
    ) {
        Text(
            text = "Einstellungen",
            fontSize = 32.sp,
            color = checkIfLightModeText(),
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            onClick = {
                themeViewModel.onThemeChanged("Dark")
            }
        ) {
            Icon(
                imageVector = Icons.Default.DarkMode,
                contentDescription = "Dark Mode",
                tint = checkIfLightModeText()
            )
            Text(
                text = "Dark mode",
                color = checkIfLightModeText(),
                modifier = Modifier.padding(3.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(311.dp)
                .height(40.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
            onClick = {
                themeViewModel.onThemeChanged("Light")
            }
        ) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Light Mode",
                tint = checkIfLightModeText()
            )
            Text(
                text = "Light mode",
                color = checkIfLightModeText(),
                modifier = Modifier.padding(3.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.BottomCenter
        ) {
            TextButton(
                onClick = { showDialog.value = true }) {
                Text(text = "Lerne mehr über Vienna Calling", color = checkIfLightModeText())
            }
        }

        if (showDialog.value) {
            Alert(
                showDialog = showDialog.value
            ) { showDialog.value = false }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun AnnotatedClickableAlertText() {
    val uriHandler = LocalUriHandler.current // To open external links
    val context = LocalContext.current

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = checkIfLightModeText())) {

            append(stringResource(R.string.about_text))

            append("Datenquelle: ")

            pushStringAnnotation(tag = "URL", annotation = "https://www.wien.gv.at")
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Stadt Wien")
            }
            pop()

            append("\nKontaktiert uns unter: ")
            pushStringAnnotation(tag = "vc@sherl.at", annotation = "mailto:vc@sherl.at")

            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("vc@sherl.at")
            }
            pop()

            append("\n\nMade with \uD83C\uDF7B")
        }
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.body1,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let {
                    uriHandler.openUri("https://www.wien.gv.at")
                }

            annotatedString.getStringAnnotations(
                tag = "vc@sherl.at",
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                context.sendMail(to = "vc@deflow.at", subject = "VC - Vienna Calling")
            }
        })
}


fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.d(TAG, "Context sendMail - no mail program was found") //Handle case where no email app is available
    } catch (t: Throwable) {
        Log.d(TAG, "Context sendMail - exception ${t.localizedMessage}")//Handle potential other type of exceptions
    }
}

@Composable
fun Alert(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            title = {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.padding(top = 5.dp),
                        imageVector = Icons.Default.ConnectWithoutContact,
                        contentDescription = "Mehr über Vienna Calling"
                    )
                    Text("Vienna Calling", color = checkIfLightModeText())
                }
            },
            text = {
                AnnotatedClickableAlertText()
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK", color = checkIfLightModeText())
                }
            },
            dismissButton = {},
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(18.dp)
        )
    }
}
