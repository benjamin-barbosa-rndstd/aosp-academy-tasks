package com.example.task3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.task3.ui.theme.Task3Theme
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.widget.TextView
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.example.task3.ISystemUptimeService
import android.util.Log



class MainActivity : ComponentActivity() {

    private var uptimeService : ISystemUptimeService? = null

    /* Service Connection */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            uptimeService = ISystemUptimeService.Stub.asInterface(service)
            updateUptime()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            uptimeService = null
        }
    }
    /* onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* bind to the UptimeService */
        val intent = Intent(this, UptimeService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    /* onDestroy */
    override fun onDestroy() {
        super.onDestroy()
        if(uptimeService != null) {
            unbindService(connection)
            uptimeService = null
        }
    }

    private fun updateUptime() {
        uptimeService?.let {
            try {
                val uptime = uptimeService?.uptime ?: 0L
                Log.d("DEBUG","The system uptime is: ${formatElapsedTime(uptime)}")
                val uptimeView: TextView = findViewById(R.id.uptimeTextView)
                uptimeView.text = "The system uptime is: ${formatElapsedTime(uptime)}"
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    private fun formatElapsedTime(elapsedTimeMillis: Long): String {
        val elapsedSeconds = elapsedTimeMillis / 1000
        val elapsedMinutes = elapsedSeconds / 60
        val seconds = elapsedSeconds % 60
        val milliseconds = elapsedTimeMillis % 1000
        return String.format("%02d:%02d:%03d", elapsedMinutes, seconds, milliseconds)
    }


}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Task3Theme {
        Greeting("Android")
    }
}