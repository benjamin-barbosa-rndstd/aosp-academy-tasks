package com.example.task3

import android.app.Service
import android.app.admin.DevicePolicyManager.InstallSystemUpdateCallback
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import com.example.task3.ISystemUptimeService

class UptimeService : Service() {

    /* Implementation of the AIDL interface */
    private val binder = UptimeBinder()

    inner class UptimeBinder : ISystemUptimeService.Stub() {
        override fun getUptime() : Long {
            return SystemClock.elapsedRealtime()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}