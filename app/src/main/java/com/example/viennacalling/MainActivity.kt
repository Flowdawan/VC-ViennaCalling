package com.example.viennacalling

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viennacalling.models.xml.EventList
import com.example.viennacalling.models.xml.RssFeed
import com.example.viennacalling.navigation.AppNavigation
import com.example.viennacalling.retrofit.RetrofitInstance
import com.example.viennacalling.ui.theme.ViennaCallingTheme
import com.example.viennacalling.viewmodels.ThemeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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