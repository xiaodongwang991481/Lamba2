package com.example.xiaodongwang.lamba2

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class Lamba2 : IntentService("Lamba2") {

    override fun onHandleIntent(intent: Intent?) {
        Log.i(LOG_TAG, "receive event")
        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Service Lamba1 in processing...")
                .setContentText("Hello World!")
        val resultIntent = Intent(this, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(resultPendingIntent)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, builder.build())
    }

    companion object {
        private val LOG_TAG = "Lambda2"
    }
}
