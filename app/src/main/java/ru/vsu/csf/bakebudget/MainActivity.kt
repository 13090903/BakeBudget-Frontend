package ru.vsu.csf.bakebudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.vsu.csf.bakebudget.screens.HomeScreen
import ru.vsu.csf.bakebudget.screens.IngredientsScreen
import ru.vsu.csf.bakebudget.screens.LoginScreen
import ru.vsu.csf.bakebudget.screens.RegistrationScreen
import ru.vsu.csf.bakebudget.ui.theme.BakeBudgetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BakeBudgetTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
//                HomeScreen()
//                LoginScreen()
//                RegistrationScreen()
            }
        }
    }

    @Composable
    fun NavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable(route = "login") {
                LoginScreen(navController)
            }

            composable(route = "home") {
                HomeScreen(navController)
            }

            composable(route = "register") {
                RegistrationScreen(navController)
            }

            composable(route = "ingredients") {
                IngredientsScreen(navController)
            }
        }
    }
}