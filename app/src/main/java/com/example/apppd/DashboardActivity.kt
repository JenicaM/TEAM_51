package com.example.apppd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val wattageGeneratedButton = findViewById<Button>(R.id.wattageGeneratedButton)
        val wattageConsumedButton = findViewById<Button>(R.id.wattageConsumedButton)
        val batteryLevelButton = findViewById<Button>(R.id.batteryLevelButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        wattageGeneratedButton.setOnClickListener {
            startActivity(Intent(this, WattageGeneratedActivity::class.java))
        }

        wattageConsumedButton.setOnClickListener {
            startActivity(Intent(this, WattageConsumedActivity::class.java))
        }

        batteryLevelButton.setOnClickListener {
            startActivity(Intent(this, BatteryLevelActivity::class.java))
        }

        logoutButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
