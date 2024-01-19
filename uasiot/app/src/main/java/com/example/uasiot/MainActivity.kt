package com.example.uasiot
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Inisialisasi Firebase Cloud Messaging
        FirebaseMessaging.getInstance().subscribeToTopic("pakan")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Jika langganan berhasil, tampilkan toast
                    showToast("Berhasil subscribe ke topik pakan")
                } else {
                    // Jika langganan gagal, tampilkan toast dengan pesan kesalahan
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    showToast("Gagal subscribe ke topik gerakan. Error: $errorMessage")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
