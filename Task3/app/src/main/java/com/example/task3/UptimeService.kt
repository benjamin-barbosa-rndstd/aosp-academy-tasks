package com.example.task3

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log

class UptimeService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val uptimeMillis = SystemClock.uptimeMillis()
        val uptimeSeconds = uptimeMillis / 1000
        val uptimeMinutes = uptimeSeconds / 60
        val uptimeHours = uptimeMinutes / 60

        Log.d("UptimeService", "[@@@] System has been up for: $uptimeHours hours, ${uptimeMinutes % 60} minutes, ${uptimeSeconds % 60} seconds")

        stopSelf()
        return START_NOT_STICKY
    }
}