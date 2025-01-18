package com.example.teamdone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.teamdone.ui.theme.TeamDoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashscreen = installSplashScreen()
//        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
//        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
//        lifecycleScope.launch {
//            delay(1000)
//            keepSplashScreen = false
//        }
        enableEdgeToEdge()
        setContent {
            TeamDoneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(Modifier.padding(innerPadding), rememberNavController())
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier, navController: NavHostController) {
    AppNavHost(
        navController = navController,
        modifier = modifier,
        startDestination = NavigationItem.AuthLogin.route
    )
}