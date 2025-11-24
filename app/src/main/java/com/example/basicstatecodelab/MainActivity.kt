package com.example.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicStateCodelabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StartScreen()
                }
            }
        }
    }
}

object Screen {
    const val firstScreen = "first_screen"
    const val secondScreen = "second_screen"
    const val thirdScreen = "third_screen"
}

@Composable
fun StartScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val count = rememberSaveable { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = Screen.firstScreen,
        modifier = modifier
    ) {
        composable(route = Screen.firstScreen) {
            FirstScreen(
                count = count.intValue,
                onIncrement = { count.intValue++ },
                onNavigateToSecond = { navController.navigate(route = Screen.secondScreen)}
            )
        }

        composable(route = Screen.secondScreen) {
            SecondScreen(
                count = count.intValue,
                onNavigateToThird = { navController.navigate(Screen.thirdScreen) },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable(Screen.thirdScreen) {
            ThirdScreen(
                count = count.intValue,
                onAddTwo = { count.intValue += 2 },
                onGoFirst = {
                    navController.popBackStack(Screen.firstScreen, inclusive = false)
                }
            )
        }
    }
}

@Composable
fun FirstScreen(
    count: Int,
    onIncrement: () -> Unit,
    onNavigateToSecond: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Your number: $count, congratulations")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onIncrement) {
            Text("Add one")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateToSecond) {
            Text("Go to Second >>")
        }
    }
}

@Composable
fun SecondScreen(
    count: Int,
    onNavigateToThird: () -> Unit,
    onGoBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is the Second Screen, your number is: $count")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToThird) {
            Text("Go to Third Screen >>")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onGoBack) {
            Text("<< Go Back")
        }
    }
}

@Composable
fun ThirdScreen(
    count: Int,
    onAddTwo: () -> Unit,
    onGoFirst: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is the Third Screen, your number: $count")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddTwo) {
            Text("Add 2 to the value")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onGoFirst) {
            Text("Return to First Screen")
        }
    }
}