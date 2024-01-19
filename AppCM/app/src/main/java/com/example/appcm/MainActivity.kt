package com.example.appcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.example.appcm.databinding.ActivityMainBinding
import java.lang.Exception
class MainActivity : AppCompatActivity(){
    lateinit var b : ActivityMainBinding
    var bundle : Bundle? = null
    var topik = "appx0f"
    var type = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        Firebase.messaging.subscribeToTopic(topik)
            .addOnCompleteListener {
                var msg = "Subcribe to $topik"
                if(!it.isSuccessful) msg = "Can't subscribe to topic"
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
    }
    override fun onResume() {
        super.onResume()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(!it.isSuccessful) return@addOnCompleteListener
            b.edToken.setText(it.result!!.toString())
        }
        try {
            bundle = getIntent().getExtras()!!
        }catch (e : Exception){
            Log.e("BUNDLE","bundle is null")
        }
        if(bundle != null){
            type = bundle!!.getInt("type")
            when(type){
                0 -> {
                    b.edPromoId.setText(bundle!!.getString("promoId"))
                    b.edPromo.setText(bundle!!.getString("promo"))
                    b.edPromoUntil.setText(bundle!!.getString("promoUntil"))
                }
                1 -> {
                    b.edTitle.setText(bundle!!.getString("title"))
                    b.edBody.setText(bundle!!.getString("body"))
                }
            }
        }
    }
}