package com.example.sampleapppd

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.mikephil.charting.charts.LineChart
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var batteryLevelTextView: TextView
    private lateinit var wattageHarvestedTextView: TextView
    private lateinit var wattageConsumedTextView: TextView
    private lateinit var lineChart: LineChart
    private val bluetoothManager = BluetoothManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        batteryLevelTextView = findViewById(R.id.batteryLevelTextView)
        wattageHarvestedTextView = findViewById(R.id.wattageHarvestedTextView)
        wattageConsumedTextView = findViewById(R.id.wattageConsumedTextView)
        lineChart = findViewById(R.id.lineChart)

        registerBatteryReceiver()

        showLoginDialog()
    }

    private fun registerBatteryReceiver() {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
    }

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            batteryLevelTextView.text = "Battery Level: $level%"
        }
    }

    private fun showLoginDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_login, null)
        builder.setView(view)

        val usernameInput: EditText = view.findViewById(R.id.usernameInput)
        val passwordInput: EditText = view.findViewById(R.id.passwordInput)

        builder.setPositiveButton("Login") { _, _ ->
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            if (username == "admin" && password == "password") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                connectToBluetoothDevice()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun connectToBluetoothDevice() {
        val device: BluetoothDevice? = bluetoothManager.getPairedDevice()
        device?.let {
            bluetoothManager.connect(it)
            startDataReading()
        } ?: run {
            Toast.makeText(this, "No paired Bluetooth device found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startDataReading() {
        thread {
            while (true) {
                val data = bluetoothManager.readData()
                runOnUiThread {
                    updateUIWithData(data)
                }
                Thread.sleep(1000) // Delay for readability
            }
        }
    }

    private fun updateUIWithData(data: String?) {
        data?.let {
            // Parse data and update UI accordingly
            val parts = it.split(",")
            if (parts.size == 2) {
                wattageHarvestedTextView.text = "Wattage Harvested: ${parts[0]}W"
                wattageConsumedTextView.text = "Wattage Consumed: ${parts[1]}W"

                // Update visibility
                batteryLevelTextView.visibility = TextView.VISIBLE
                wattageHarvestedTextView.visibility = TextView.VISIBLE
                wattageConsumedTextView.visibility = TextView.VISIBLE
                lineChart.visibility = LineChart.VISIBLE

                // Optionally update the chart with new data here
                // updateChart(parts) // Implement this method as needed
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver) // Clean up receiver
    }
}
