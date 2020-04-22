package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var isRunning = false   // variable to indicate the state of FAB(Floating Action Button)
    private var time = 0
    private var timerTask: Timer? = null    // variable with type class Timer
    private var lap = 1 // variable to store order value of recordLapTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{     // when the FAB is clicked
            isRunning = !isRunning  // change the state of FAB

            if(isRunning){
                start()
            }else{
                pause()
            }
        }

        lapButton.setOnClickListener{     // when the lapButton is clicked
            recordLapTime()
        }

        resetFab.setOnClickListener{       // when the reset button is clicked
            reset()
        }
    }

    private fun start(){
        fab.setImageResource(R.drawable.ic_pause_black_24dp)    // change FAB image "start" -> "pause"

        timerTask = timer(period = 10){     // Operation with period 0.01 second
            time++

            var sec = time / 100
            var milli = time % 100
            runOnUiThread {     // display sec and millisec data
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)    // change FAB image "pause" -> "start"
        timerTask?.cancel() // Cancel running timer
    }

    private fun recordLapTime(){
        val lapTime = this.time
        val textView = TextView(this)   // generate TextView type object
        textView.text = "$lap LAP: ${lapTime / 100}.${lapTime % 100}"

        // Insert lap time on top
        lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset(){
        timerTask?.cancel() // Cancel running timer

        // Initialize all variables
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        // Remove all laptime
        lapLayout.removeAllViews()
        lap = 1
    }
}
