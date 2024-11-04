package com.example.apppd

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BatteryLevelActivity : AppCompatActivity() {

    private lateinit var batteryImageView: ImageView
    private lateinit var batteryLevelTextView: TextView
    private var batteryLevel: Int = 0 // Placeholder for the battery level percentage (0-100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery_level)

        batteryImageView = findViewById(R.id.batteryImageView)
        batteryLevelTextView = findViewById(R.id.batteryLevelTextView)

        // Simulate receiving battery data (replace this with actual data from Bluetooth/Wi-Fi)
        batteryLevel = receiveBatteryData()

        // Update UI based on received battery level
        updateBatteryIcon(batteryLevel)
        batteryLevelTextView.text = "$batteryLevel% Remaining"
    }

    // Simulate receiving data from a Bluetooth or Wi-Fi connection
    private fun receiveBatteryData(): Int {
        // Replace this with actual Bluetooth/Wi-Fi data retrieval logic
        // For example, you might retrieve data from BluetoothManager or network service.
        return 70 // Placeholder value; replace with actual battery level data
    }

    // Update the battery icon based on the battery level percentage
    private fun updateBatteryIcon(level: Int) {
        val batteryIcon = when {
            level > 75 -> R.drawable.battery_full
            level > 50 -> R.drawable.battery_half
            level > 25 -> R.drawable.battery_low
            else -> R.drawable.battery_empty
        }
        batteryImageView.setImageResource(batteryIcon)
    }
}
