package com.example.sipordimobile_4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sipordimobile_4.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SignUpActivity: AppCompatActivity(), View.OnClickListener {
    lateinit var auth: FirebaseAuth
    lateinit var b: ActivitySignupBinding
    var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.btnRegister.setOnClickListener(this)
        b.txSignIn.setOnClickListener(this)
        auth = Firebase.auth
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnRegister -> {
                auth.createUserWithEmailAndPassword(
                    b.edRegEmail.text.toString(),
                    b.edRegPassword.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            currentUser = auth.currentUser
                            if (currentUser != null) {
                                currentUser!!.updateProfile(
                                    userProfileChangeRequest {
                                        displayName = b.edRegUsername.text.toString()
                                    }
                                )
                                currentUser!!.sendEmailVerification()
                            }
                            Toast.makeText(
                                this,
                                "Berhasil mendaftarkan user, " +
                                        "silahkan cek email anda untuk verifikasi",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            // Handle error and log error message
                            Log.e("SignUpActivity", "Gagal mendaftar: ${task.exception}")
                            Toast.makeText(
                                this, "Tidak dapat mendaftar: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            R.id.txSignIn ->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
