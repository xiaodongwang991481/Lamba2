package com.example.xiaodongwang.lamba2

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class Lamba2 : IntentService("Lamba2") {

    private fun startInForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = Notification.Builder(this)
                .setContentTitle("Lambda2Service")
                .setContentText("lambda2 service")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setTicker("TICKER")
                .setAutoCancel(false)
                .setWhen(System.currentTimeMillis())
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .build()
        startForeground(101, notification)
    }

    override fun onCreate() {
        Log.i(LOG_TAG, "create service")
        super.onCreate()
        createNotificationChannel()
        startInForeground()
    }

    override fun onDestroy() {
        Log.i(LOG_TAG, "destory service")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.i(LOG_TAG, "bind service")
        return super.onBind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(LOG_TAG, "unbind service")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(LOG_TAG, "receive start command")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i(LOG_TAG, "receive event")
        if (intent == null) {
            Log.e(LOG_TAG, "intent missing")
            return
        }
        var name = ""
        if (intent.hasExtra("name")) {
            name = intent.getStringExtra("name")
        } else {
            Log.i(LOG_TAG, "name is missing in intent")
        }
        var payload = ""
        if (intent.hasExtra("payload")) {
            payload = intent.getStringExtra("payload")
        } else {
            Log.i(LOG_TAG, "payload is missing in intent")
        }
        val sendIntent = Intent("com.example.xiaodongwang.lambdamaster.resultreceiver")
        sendIntent.setPackage("com.example.xiaodongwang.lambdamaster")
        sendIntent.putExtra("name", name)
        sendIntent.putExtra("payload", payload)
        Log.i(LOG_TAG, "broadcast message $name=$payload")
        sendBroadcast(sendIntent)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val mChannel = NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_MAX
            )
            mChannel.enableLights(true)
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.lightColor = Color.RED
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    companion object {
        private val LOG_TAG = "Lambda2"
        private val NOTIFICATION_CHANNEL_ID = "lambda2"
        private val NOTIFICATION_CHANNEL_NAME = "lambda2"
    }
}
