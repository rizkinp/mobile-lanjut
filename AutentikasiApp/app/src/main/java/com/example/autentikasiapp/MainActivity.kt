package com.example.autentikasiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.autentikasiapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth

import com.google.firebase.ktx.Firebase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var auth: FirebaseAuth
    lateinit var b : ActivityMainBinding
    var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnSignIn.setOnClickListener(this)
        b.txSignUp.setOnClickListener(this)

        auth = Firebase.auth
    }
    override fun onClick(p0: View?) { when(p0?.id){
        R.id.btnSignIn ->{ auth.signInWithEmailAndPassword(
            b.edUsername.text.toString(), b.edPassword.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful){
                    currentUser = auth.currentUser
                    if(currentUser!=null){
                        if (currentUser!!. isEmailVerified){ Toast.makeText(this,
                            "Login berhasil", Toast.LENGTH_LONG).show()
                            val intent =
                            Intent(this,DashboardActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,
                                "Email anda belum terverifikasi", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Username/password salah", Toast.LENGTH_LONG).show()
                }
            }
        }
        R.id.txSignUp ->{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    }
}
