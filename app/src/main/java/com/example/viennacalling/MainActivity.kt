package com.example.viennacalling

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viennacalling.navigation.AppNavigation
import com.example.viennacalling.ui.theme.ViennaCallingTheme
import com.example.viennacalling.viewmodels.LoginViewModel
import com.example.viennacalling.viewmodels.ThemeViewModel

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
fun MyApp(themeViewModel: ThemeViewModel = viewModel(),
          content: @Composable () -> Unit) {
    val darkMode by remember {
        themeViewModel.darkMode
    }
    ViennaCallingTheme (darkTheme = darkMode) {
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