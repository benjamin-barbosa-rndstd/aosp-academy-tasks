package com.example.task2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import com.example.task2.ui.theme.Task2Theme
import kotlinx.coroutines.launch
import android.widget.TextView
import android.os.Handler


class MainActivity : ComponentActivity() {
    private var startTimeMillis: Long = 0
    private lateinit var foregroundTimeTextView: TextView
    private val handler = Handler()

    private val updateTimeTask = object : Runnable {
        override fun run() {
            val elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis
            val elapsedSeconds = elapsedTimeMillis / 1000
            val elapsedMinutes = elapsedSeconds / 60
            val seconds = elapsedSeconds / 60
            val milliseconds = elapsedTimeMillis % 1000
            foregroundTimeTextView.text = String.format("%02d:%02d:%03d", elapsedMinutes, elapsedSeconds, elapsedTimeMillis)
            handler.postDelayed(this, 100) // Update every 100 milliseconds
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foregroundTimeTextView = findViewById(R.id.foregroundTime)
    }
    override fun onStart() {
        super.onStart()
        startTimeMillis = System.currentTimeMillis()
        handler.post(updateTimeTask)
    }
    override fun onResume() {
        super.onResume()
        handler.post(updateTimeTask)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateTimeTask)
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