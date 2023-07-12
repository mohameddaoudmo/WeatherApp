package com.example.weatherapplication.alart

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.weatherapplication.MainActivity


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
            var importance = NotificationManager.IMPORTANCE_HIGH
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
//            val sound = Uri.parse(
//                "android.resource://" + ApplicationProvider.getApplicationContext<Context>()
//                    .getPackageName() + "/" + com.example.weatherapplication.R.raw.sound
//            )


            val notification = NotificationCompat.Builder(context, STATUS_CHANNEL_ID)

                .setSmallIcon(com.example.weatherapplication.R.drawable.baseline_add_alert_24)
                .setContentTitle("Weather Status")
                .setContentText("The Status of weather now is $text in $land")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            if (true) {


            } else {
               notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)

            }

//            notificationManager.notify(1, notification.build()
                    with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, notification.build())
            }        }


    }
    private fun getFullScreenIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {
        const val STATUS_CHANNEL_ID = "StatusofWether_channel"
    }
}