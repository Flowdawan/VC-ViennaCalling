package at.deflow.viennacalling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import at.deflow.viennacalling.navigation.AppNavigation
import at.deflow.viennacalling.ui.theme.ViennaCallingTheme
import at.deflow.viennacalling.viewmodels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            MyApp {
                AppNavigation(themeViewModel)
            }
        }
    }
}

@Composable
fun MyApp(
    themeViewModel: ThemeViewModel = viewModel(),
    content: @Composable () -> Unit
) {
    val darkMode by remember {
        themeViewModel.darkMode
    }
    ViennaCallingTheme(darkTheme = darkMode) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        AppNavigation()
    }
}