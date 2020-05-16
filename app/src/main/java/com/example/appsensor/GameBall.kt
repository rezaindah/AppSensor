package com.example.appsensor

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

class GameBall : AppCompatActivity(), SensorEventListener {
    private var ball = ShapeDrawable()
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var animatedView: AnimatedView? = null
    var wallTouched = false
    var displayWidth = 0
    var displayHeight = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        counter = 0
        animatedView = AnimatedView(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayWidth = displayMetrics.widthPixels - 100
        displayHeight = displayMetrics.heightPixels - 100
        xAcceleration = displayWidth / 2
        yAcceleration = displayHeight / 2
        Log.v("Y Size:", Integer.toString(displayHeight))
        Log.v("X Size:", Integer.toString(displayWidth))
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST)
        }
        setContentView(animatedView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            xAcceleration -= event.values[0].toInt()
            yAcceleration += event.values[1].toInt()
            if (xAcceleration > displayWidth) {
                xAcceleration = displayWidth
                gameOver()
            } else if (xAcceleration < 0) {
                xAcceleration = 0
                gameOver()
            }
            if (yAcceleration > displayHeight - 100) {
                yAcceleration = displayHeight - 100
                gameOver()
            } else if (yAcceleration < 0) {
                yAcceleration = 0
                gameOver()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    fun gameOver() {
        wallTouched = true
        counter++
        resetBallPosition()
        if (counter > 0) {
            onPause()
            Toast.makeText(applicationContext, "Game over. Back to menu", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this@GameBall, MainActivity::class.java)
                startActivity(intent)
            }, 1000)
        }
    }

    fun resetBallPosition() {
        yAcceleration = displayHeight / 2
        xAcceleration = displayWidth / 2
    }

    inner class AnimatedView(context: Context?) : AppCompatImageView(context) {
        var p = Paint()
        override fun onDraw(canvas: Canvas) {
            p.color = Color.BLACK
            p.textSize = 80f
            ball.setBounds(xAcceleration, yAcceleration, xAcceleration + Companion.width, yAcceleration + Companion.height)
            canvas.drawColor(Color.parseColor("#d1d8e0"))
            canvas.drawText("Wall touched: " + Integer.toString(counter), 100f, 100f, p)
            ball.draw(canvas)
            invalidate()
        }

        init {
            ball = ShapeDrawable(OvalShape())
            ball.paint.color = Color.parseColor("#4b7bec")
        }
    }

    companion object {
        var xAcceleration = 0
        var yAcceleration = 0
        var counter = 0
        var xVelocity = 0f
        var yVelocity = 0.0f
        var xPosition = 0f
        var yPosition = 0.0f
        const val width = 100
        const val height = 100
    }
}
