package com.example.sinu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tampilkan layout Splash Screen
        setContentView(R.layout.activity_splash)

        // Thread untuk menunggu beberapa detik sebelum pindah ke tampilan utama
        val splashThread = object : Thread() {
            override fun run() {
                try {
                    // Waktu tampilan Splash Screen (dalam milidetik)
                    sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    // Pindah ke tampilan utama (misalnya MainActivity)
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        splashThread.start()
    }
}
