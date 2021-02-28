package ve.com.cblanco1989.features.accelerometertest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ve.com.cblanco1989.R
import java.util.*


class AccelerometerActivity : AppCompatActivity(), SensorEventListener {


    val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    var timestamp: Long = 0


    val TAG = javaClass.name
    var mSensorManager: SensorManager? = null
    var mAccelerometer: Sensor? = null
    var updateTimer: Timer? = null
    var linearAcceleration = FloatArray(3)
    var velocity: Velocity? = null
    var handler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        var mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);


    }

    override fun onSensorChanged(event: SensorEvent?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}