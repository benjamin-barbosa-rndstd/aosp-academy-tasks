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

/* Extra imports */
import android.widget.TextView
import android.os.Handler
import android.widget.Button


class MainActivity : ComponentActivity() {
    /* Time variables */
    private var startTimeMillis: Long = 0
    private var startBackGroundTime: Long = 0
    private var startForeGroundTime: Long = 0

    /* To manage layouts */
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

    /* In Background */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stateView: TextView = findViewById(R.id.stateView)
        stateView.text = "onCreate"


        foregroundTimeTextView = findViewById(R.id.foregroundTime)

        /* For state button */
        val stateButton: Button = findViewById(R.id.stateButton)
        stateButton.setOnClickListener {
            stateView.text = "Button clicked"
            finish()
        }
    }

    /* In Background */
    override fun onStart() {
        super.onStart()
        val stateView: TextView = findViewById(R.id.stateView)
        stateView.text = "onStart"
    }

    /* In Foreground */
    override fun onResume() {
        super.onResume()
        val stateView: TextView = findViewById(R.id.stateView)
        stateView.text = "onResume"

        startTimeMillis = System.currentTimeMillis()
        handler.post(updateTimeTask)
    }

    /* In Background */
    override fun onPause() {
        super.onPause()
        val stateView: TextView = findViewById(R.id.stateView)
        stateView.text = "onPause"
    }

    /* In Background? */
    override fun onStop() {
        super.onStop()
        val stateView: TextView = findViewById(R.id.stateView)
        stateView.text = "onPause"

        handler.removeCallbacks(updateTimeTask)
    }

    /*  */
    override fun onDestroy() {
        super.onDestroy()
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