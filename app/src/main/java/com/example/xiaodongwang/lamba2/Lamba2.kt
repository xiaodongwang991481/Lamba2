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
            Log.i(LOG_TAG, "payload is missing in intent")
        }
        val sendIntent = Intent("com.example.xiaodongwang.lambdamaster.resultreceiver")
        sendIntent.setPackage("com.example.xiaodongwang.lambdamaster")
        sendIntent.putExtra("name", name)
        sendIntent.putExtra("payload", payload)
        sendBroadcast(sendIntent)
    }

    companion object {
        private val LOG_TAG = "Lambda2"
    }
}
