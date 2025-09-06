// app/src/main/java/com/example/dnicksaklarpintar/presentation/main/MainScreen.kt

package com.example.dnicksaklarpintar.presentation.main

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnicksaklarpintar.R
import com.example.dnicksaklarpintar.ui.theme.DnickSaklarPintarTheme
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current as FragmentActivity
    val switchState by mainViewModel.switchState.collectAsState() // Ambil status tunggal dari ViewModel
    val isUpdating by mainViewModel.isUpdating.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val executor: Executor = remember { Executors.newSingleThreadExecutor() }
    val biometricPrompt: BiometricPrompt = remember {
        BiometricPrompt(
            context,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Autentikasi gagal: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    coroutineScope.launch {
                        mainViewModel.toggleSwitchState() // Panggil toggle untuk satu saklar
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(context, "Autentikasi gagal.", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    val promptInfo: BiometricPrompt.PromptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verifikasi Identitas")
            .setSubtitle("Sentuh sensor sidik jari Anda untuk mengontrol saklar.")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kontrol Saklar", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Column( // Menggunakan Column untuk satu tampilan saklar
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp), // Padding di sekitar konten
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ikon Lampu
            val iconColor = if (switchState) Color.Yellow else Color.Gray // Warna ikon sesuai status
            val iconResource = if (switchState) R.drawable.ic_light_on else R.drawable.ic_light_off // Resource ikon

            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = if (switchState) "Lampu Menyala" else "Lampu Mati",
                tint = iconColor,
                modifier = Modifier
                    .size(240.dp) // Ukuran ikon yang lebih besar
                    .padding(bottom = 32.dp) // Jarak dengan teks di bawahnya
            )

            // Teks status ON/OFF
            Text(
                text = if (switchState) "ON" else "OFF",
                style = MaterialTheme.typography.displayLarge,
                color = if (switchState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Tombol Toggle
            Button(
                onClick = {
                    val biometricManager = BiometricManager.from(context)
                    val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)

                    when (canAuthenticate) {
                        BiometricManager.BIOMETRIC_SUCCESS -> {
                            biometricPrompt.authenticate(promptInfo)
                        }
                        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                            Toast.makeText(context, "Perangkat ini tidak memiliki sensor biometrik.", Toast.LENGTH_LONG).show()
                            coroutineScope.launch { mainViewModel.toggleSwitchState() } // Langsung toggle jika tidak ada hardware
                        }
                        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                            Toast.makeText(context, "Sensor biometrik tidak tersedia saat ini.", Toast.LENGTH_LONG).show()
                        }
                        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                            Toast.makeText(context, "Tidak ada sidik jari atau kunci layar terdaftar. Harap daftarkan di Pengaturan.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            Toast.makeText(context, "Autentikasi biometrik tidak dapat digunakan.", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                enabled = !isUpdating, // Tombol dinonaktifkan saat sedang update
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary) // Indikator loading
                } else {
                    Text(
                        text = if (switchState) "MATIKAN SAKLAR" else "NYALAKAN SAKLAR",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

