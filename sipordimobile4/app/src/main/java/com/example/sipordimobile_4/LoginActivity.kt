package com.example.sipordimobile_4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sipordimobile_4.R
import com.example.sipordimobile_4.databinding.ActivityLoginBinding
import com.example.sipordimobile_4.MainActivity
import com.example.sipordimobile_4.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // Tombol untuk login dengan email dan password
        val btnSignInWithEmail = findViewById<Button>(R.id.btnSignInWithEmail)
        btnSignInWithEmail.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            signInWithEmail(email, password)
        }

        // Konfigurasi Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // Tombol untuk login dengan Google
        val btnSignInWithGoogle = findViewById<Button>(R.id.btnSignInWithGoogle)
        btnSignInWithGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    // Fungsi untuk menangani hasil dari login Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Gagal login dengan Google.", e)
            }
        }
    }

    // Fungsi untuk mengautentikasi ke Firebase menggunakan akun Google
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("SignInWithGoogle", "Login berhasil: ${user?.uid}")
                } else {
                    Log.e("SignInWithGoogle", "Gagal login dengan Google.", task.exception)
                }
            }
    }

    // Fungsi untuk login dengan email dan password
    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("SignInWithEmail", "Login berhasil: ${user?.uid}")
                } else {
                    Log.e("SignInWithEmail", "Gagal login.", task.exception)
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}

