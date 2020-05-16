package com.example.appsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class SensorMgr : AppCompatActivity() {
    private lateinit var mSensorManager: SensorManager
    private lateinit var listView: ListView
    var List: List<Sensor>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_mgr)
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        listView=findViewById(R.id.listView1)
        val sensor=mSensorManager.getSensorList(Sensor.TYPE_ALL)

        listView.setAdapter(ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1,  sensor))
        //val adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,sensor)

        //listView.adapter=adapter
        //val deviceSensors: List<Sensor> = mSensorManager.getSensorList(Sensor.TYPE_ALL)


    }

}
