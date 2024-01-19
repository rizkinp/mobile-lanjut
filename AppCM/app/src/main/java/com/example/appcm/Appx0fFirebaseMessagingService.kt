package com.example.appcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
class Appx0fFirebaseMessagingService : FirebaseMessagingService() {
    var promoId = ""
    var promo = ""
    var promoUntil = ""
    var body = ""
    var title = ""
    val RC_INTENT = 100
    val CHANNEL_ID = "appx0f"
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //if the incoming message is a data, get its content
        if(p0.getData().size > 0){
            promoId = p0.data.getValue("promoId")
            promo = p0.data.getValue("promo")
            promoUntil = p0.data.getValue("promoUntil")
            intent.putExtra("promoId",promoId)
            intent.putExtra("promo", promo)
            intent.putExtra("promoUntil",promoUntil)
            intent.putExtra("type",0) //0 = data
            sendNotif("PROMO NIPUN MAS!!!", "$promo $promoUntil", intent)
        }
        //if the incoming message is a notification, get its title and body
        if(p0.notification != null){
            body = p0.notification!!.body!!
            title = p0.notification!!.title!!
            intent.putExtra("title",title)
            intent.putExtra("body", body)
            intent.putExtra("type",1) //1 = notification
            sendNotif(title, body, intent)
        }
    }
    //this function will be called when there is a data message
    fun sendNotif(title : String, body : String, intent : Intent){
        //this pendingIntent will be called when there is an incoming message
        //and invoked the predefined intent
        val pendingIntent =
            PendingIntent.getActivity(this, RC_INTENT, intent,
                PendingIntent.FLAG_ONE_SHOT or
                        PendingIntent.FLAG_IMMUTABLE
            )
        //choose a ringtone
        val ringtoneUri = RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_RINGTONE)
        val audioAttributes = AudioAttributes.Builder().
        setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE).
        setContentType(
            AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        //create a notification manager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as
                    NotificationManager
        //When we target Android 8.0 (API level 26),
        //we must implement one or more notification channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(CHANNEL_ID, "appx0f",
                NotificationManager.IMPORTANCE_HIGH )
            mChannel.description = "App x0f"
            mChannel.setSound(ringtoneUri,audioAttributes)
            notificationManager.createNotificationChannel(mChannel)
        }
        //build a notification
        val notificationBuilder =
            NotificationCompat.Builder(baseContext,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(
                    resources,R.drawable.ic_launcher_background))
                .setContentTitle(title)
                .setContentText(body)
                .setSound(ringtoneUri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build()
        notificationManager.notify(RC_INTENT,notificationBuilder)
    }
}
