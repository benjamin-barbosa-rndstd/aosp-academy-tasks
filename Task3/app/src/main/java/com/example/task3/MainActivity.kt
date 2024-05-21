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
import android.widget.TextView
import android.content.Context
import android.content.Intent
import android.content.IntentFilter



class MainActivity : ComponentActivity() {

    private lateinit var uptimeTextView: TextView
    private val uptimeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val uptime = intent?.getLongExtra("uptime", 0)
            uptimeTextView.text = "System Uptime: $uptime seconds"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uptimeTextView = findViewById(R.id.uptimeTextView)

        // Register the receiver to get uptime updates
        registerReceiver(uptimeReceiver, IntentFilter("com.example.uptimeservice.UPTIME"))

        // Start the uptime service
        val serviceIntent = Intent(this, UptimeService::class.java)
        startService(serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(uptimeReceiver)
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