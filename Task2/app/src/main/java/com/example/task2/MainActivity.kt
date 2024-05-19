package com.example.task2

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import android.widget.TextView
import com.example.task2.ui.theme.Task2Theme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    /* Time variables */
    private var startTimeMillis: Long = 0
    private var foregroundStartTimeMillis: Long = 0
    private var backgroundStartTimeMillis: Long = 0
    private var totalForegroundTimeMillis: Long = 0
    private var totalBackgroundTimeMillis: Long = 0

    /* To manage layouts */
    private lateinit var foregroundTimeTextView: TextView
    private lateinit var backgroundTimeTextView: TextView

    private val handler = Handler()

    private val updateForegroundTimeTask = object : Runnable {
        override fun run() {
            val elapsedTimeMillis = totalForegroundTimeMillis + (System.currentTimeMillis() - foregroundStartTimeMillis)
            val elapsedSeconds = elapsedTimeMillis / 1000
            val elapsedMinutes = elapsedSeconds / 60
            val seconds = elapsedSeconds % 60
            val milliseconds = elapsedTimeMillis % 1000
            foregroundTimeTextView.text = String.format("ForegroundTime: %02d:%02d:%03d", elapsedMinutes, seconds, milliseconds)
            handler.postDelayed(this, 100) // Update every 100 milliseconds
        }
    }

    private val updateBackgroundTimeTask = object : Runnable {
        override fun run() {
            val elapsedTimeMillis = (System.currentTimeMillis() - backgroundStartTimeMillis)
            val elapsedSeconds = elapsedTimeMillis / 1000
            val elapsedMinutes = elapsedSeconds / 60
            val seconds = elapsedSeconds % 60
            val milliseconds = elapsedTimeMillis % 1000
            backgroundTimeTextView.text = String.format("BackgroundTime: %02d:%02d:%03d", elapsedMinutes, seconds, milliseconds)
            handler.postDelayed(this, 100) // Update every 100 milliseconds
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startTimeMillis = System.currentTimeMillis()

        foregroundTimeTextView = findViewById(R.id.foregroundTime)
        backgroundTimeTextView = findViewById(R.id.backgroundTime)

        // Start updating background time immediately as the app starts
        backgroundStartTimeMillis = System.currentTimeMillis()
        handler.post(updateBackgroundTimeTask)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        // Start tracking foreground time
        foregroundStartTimeMillis = System.currentTimeMillis()
        handler.post(updateForegroundTimeTask)
    }

    override fun onPause() {
        super.onPause()
        // Stop tracking foreground time
        totalForegroundTimeMillis += System.currentTimeMillis() - foregroundStartTimeMillis
        handler.removeCallbacks(updateForegroundTimeTask)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up any remaining handlers
        handler.removeCallbacks(updateForegroundTimeTask)
        handler.removeCallbacks(updateBackgroundTimeTask)
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
    Task2Theme {
        Greeting("Android")
    }
}
