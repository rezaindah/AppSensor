package com.example.appsensor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btnSensor)
        button.setOnClickListener{
            val intent = Intent(this, SensorMgr::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.btnGPS)
        button2.setOnClickListener{
            val intent = Intent(this, TrackGps::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.btnGame)
        button3.setOnClickListener{
            val intent = Intent(this, GameBall::class.java)
            startActivity(intent)
        }
    }
}
