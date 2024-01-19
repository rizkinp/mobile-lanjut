package com.example.autentikasiapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.autentikasiapp.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DashboardActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var auth : FirebaseAuth
    lateinit var b : ActivityDashboardBinding
    var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        b = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.btnProfil.setOnClickListener(this)
        b.btnInputData.setOnClickListener(this)
        b.btnLogOut.setOnClickListener(this)

        auth = Firebase.auth
    }

    override fun onClick(p0: View?) { when(p0?.id){
        R.id.btnProfil ->{
            currentUser = auth.currentUser
            if(currentUser!=null){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("User Profile").setMessage(
                    "Nama : ${currentUser!!.displayName}\nEmail : "+ "${currentUser!!.email}"
                ).setNeutralButton("OK",null)
                builder.show()
            }
        }
        R.id.btnLogOut ->{ auth.signOut()
            finish()
        }
        R.id.btnInputData ->{
//TODO : CALL SOME INTENT
        }
    }
    }
}