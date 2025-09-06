// app/src/main/java/com/example/dnicksaklarpintar/navigation/AppNavHost.kt
package com.example.dnicksaklarpintar.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dnicksaklarpintar.presentation.auth.AuthScreen
import com.example.dnicksaklarpintar.presentation.main.MainScreen

object Route {
    const val LOGIN = "login"
    const val HOME = "home"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String, // Tambahkan parameter startDestination di sini
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination, // Gunakan parameter yang diterima
        modifier = modifier
    ) {
        composable(Route.LOGIN) {
            AuthScreen(
                onLoginSuccess = {
                    navController.navigate(Route.HOME) {
                        // Hapus stack login agar tidak bisa kembali ke login setelah sukses
                        popUpTo(Route.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.HOME) {
            MainScreen()
        }
    }
}