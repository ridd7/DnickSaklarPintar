// app/src/main/java/com/example/dnicksaklarpintar/presentation/auth/AuthViewModel.kt
package com.example.dnicksaklarpintar.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }

    fun clearAuthError() {
        _authError.value = null
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    suspend fun login(): Boolean {
        _isLoading.value = true
        _authError.value = null // Clear previous errors
        return try {
            auth.signInWithEmailAndPassword(email.value, password.value).await()
            _isLoading.value = false
            true
        } catch (e: Exception) {
            _isLoading.value = false
            val errorMessage = when (e) {
                is FirebaseAuthException -> {
                    when (e.errorCode) {
                        "ERROR_INVALID_EMAIL" -> "Email tidak valid."
                        "ERROR_WRONG_PASSWORD" -> "Password salah."
                        "ERROR_USER_NOT_FOUND" -> "Akun tidak ditemukan."
                        "ERROR_USER_DISABLED" -> "Akun dinonaktifkan."
                        else -> "Login gagal: ${e.localizedMessage ?: "Terjadi kesalahan."}"
                    }
                }
                else -> "Login gagal: ${e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui."}"
            }
            _authError.value = errorMessage
            false
        }
    }

    suspend fun register(): Boolean {
        _isLoading.value = true
        _authError.value = null // Clear previous errors
        return try {
            auth.createUserWithEmailAndPassword(email.value, password.value).await()
            _isLoading.value = false
            true
        } catch (e: Exception) {
            _isLoading.value = false
            val errorMessage = when (e) {
                is FirebaseAuthException -> {
                    when (e.errorCode) {
                        "ERROR_EMAIL_ALREADY_IN_USE" -> "Email sudah terdaftar."
                        "ERROR_WEAK_PASSWORD" -> "Password terlalu lemah. Minimal 6 karakter."
                        "ERROR_INVALID_EMAIL" -> "Email tidak valid."
                        else -> "Pendaftaran gagal: ${e.localizedMessage ?: "Terjadi kesalahan."}"
                    }
                }
                else -> "Pendaftaran gagal: ${e.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui."}"
            }
            _authError.value = errorMessage
            false
        }
    }
}