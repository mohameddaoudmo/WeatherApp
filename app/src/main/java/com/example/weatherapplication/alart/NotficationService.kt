package com.example.weatherapplication.alart

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherapplication.R

class NotficationService(
    private val context: Context
) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotficicationChannal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val charSequence :CharSequence ="Weather Status"
            var discrption : String = "Channel for Notification for Weather Status"
            var importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                STATUS_CHANNEL_ID ,charSequence    , importance
            )
            channel.description=discrption
            notificationManager.createNotificationChannel(channel)




        }}

    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(text: String, statusofNotfication: Boolean,land :String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotficicationChannal()


            val notification = NotificationCompat.Builder(context, STATUS_CHANNEL_ID)

                .setSmallIcon(R.drawable.baseline_add_alert_24)
                .setContentTitle("Weather Status")
                .setContentText("The Status of weather now is $text in $land")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            if (statusofNotfication) {
                notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            } else {
                notification.setSound(null);
            }

            notificationManager.notify(1, notification.build())
        }


    }

    companion object {
        const val STATUS_CHANNEL_ID = "StatusofWether_channel"
    }
}