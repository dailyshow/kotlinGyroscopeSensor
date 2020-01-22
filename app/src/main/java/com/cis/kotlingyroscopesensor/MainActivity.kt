package com.cis.kotlingyroscopesensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// 가속도 센서는 기울어짐의 정도를 측정할 수 있고
// 자이로스코프 센서를 이용하면 각 축의 회전 각 속도를 측정할 수 있다.
class MainActivity : AppCompatActivity() {
    var manager : SensorManager? = null
    var listener : SensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        startBtn.setOnClickListener {
            val sensor = manager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            val chk = manager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
            if (chk == false) {
                tv.text = "자이로스코프 센서를 지원하지 않습니다."
            }
        }

        stopBtn.setOnClickListener {
            manager?.unregisterListener(listener)
        }
    }

    inner class SensorListener : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
                tv.text = "x축 각 속도 : ${event?.values[0]}\n"
                tv.append("y축 각 속도 : ${event?.values[1]}\n")
                tv.append("z축 각 속도 : ${event?.values[2]}")
            }
        }

    }
}
