// app/src/main/java/com/example/dnicksaklarpintar/presentation/main/MainViewModel.kt

package com.example.dnicksaklarpintar.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log // Tambahkan import ini untuk Log

class MainViewModel : ViewModel() {

    // StateFlow untuk satu status saklar (sesuai Firebase Anda saat ini)
    private val _switchState = MutableStateFlow(false)
    val switchState: StateFlow<Boolean> = _switchState

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val userId = auth.currentUser?.uid

    // Path yang sesuai dengan struktur Firebase Anda ("saklar_status")
    private val SAKLAR_PATH = "saklar_status"

    init {
        // Log status user ID untuk debugging
        Log.d("MainViewModel", "Current User ID: $userId")
        if (userId != null) {
            // Ambil status saklar dari Firebase saat ViewModel diinisialisasi
            listenToSwitchState()
        } else {
            Log.e("MainViewModel", "User not logged in. Cannot fetch switch state.")
        }
    }

    private fun listenToSwitchState() {
        // Listener untuk perubahan data di Firebase
        database.getReference(SAKLAR_PATH) // Baca langsung dari "saklar_status"
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Pastikan data yang dibaca adalah Boolean
                    val isOn = snapshot.getValue(Boolean::class.java) ?: false
                    _switchState.value = isOn
                    Log.d("MainViewModel", "Firebase switch state updated to: $isOn")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainViewModel", "Firebase read error: ${error.message}", error.toException())
                }
            })
    }

    // Fungsi untuk memperbarui status saklar di Firebase
    fun toggleSwitchState() {
        // Pastikan user sudah login dan tidak sedang dalam proses update lain
        if (userId == null || _isUpdating.value) {
            Log.w("MainViewModel", "Cannot toggle: User ID is null or already updating.")
            return
        }

        viewModelScope.launch {
            _isUpdating.value = true // Set status updating
            val newIsOn = !_switchState.value // Toggle status saat ini

            database.getReference(SAKLAR_PATH) // Tulis kembali ke "saklar_status"
                .setValue(newIsOn)
                .addOnSuccessListener {
                    _isUpdating.value = false
                    Log.d("MainViewModel", "Successfully updated switch state to $newIsOn")
                }
                .addOnFailureListener {
                    _isUpdating.value = false
                    Log.e("MainViewModel", "Failed to update switch state: ${it.message}", it)
                }
        }
    }
}