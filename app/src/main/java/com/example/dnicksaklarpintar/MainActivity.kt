// app/src/main/java/com/example/dnicksaklarPintar/MainActivity.kt
package com.example.dnicksaklarpintar

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.dnicksaklarpintar.navigation.AppNavHost
import com.example.dnicksaklarpintar.navigation.Route
import com.example.dnicksaklarpintar.presentation.auth.AuthViewModel
import com.example.dnicksaklarpintar.ui.theme.DnickSaklarPintarTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DnickSaklarPintarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()

                    val startDestination = if (authViewModel.isUserLoggedIn()) {
                        Route.HOME
                    } else {
                        Route.LOGIN
                    }

                    // Panggil AppNavHost dengan parameter startDestination yang baru
                    AppNavHost(navController = navController, startDestination = startDestination)
                }
            }
        }
    }
}