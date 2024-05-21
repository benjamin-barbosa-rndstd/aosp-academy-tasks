package com.example.task3

import android.app.Service
import android.content.Intent
import android.os.IBinder

class UptimeService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val uptime = getSystemUptime()
        Log.d("UptimeService", "System uptime: $uptime seconds")

        // Optionally broadcast the uptime
        val uptimeIntent = Intent("com.example.uptimeservice.UPTIME")
        uptimeIntent.putExtra("uptime", uptime)
        sendBroadcast(uptimeIntent)

        return START_NOT_STICKY
    }

    private fun getSystemUptime(): Long {
        // SystemClock.elapsedRealtime() returns the time since boot, including sleep time, in milliseconds
        return SystemClock.elapsedRealtime() / 1000 // convert to seconds
    }
}