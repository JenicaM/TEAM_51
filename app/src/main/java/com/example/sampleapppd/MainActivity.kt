package com.example.sampleapppd

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart

class MainActivity : AppCompatActivity() {

    private lateinit var batteryLevelTextView: TextView
    private lateinit var wattageHarvestedTextView: TextView
    private lateinit var wattageConsumedTextView: TextView
    private lateinit var lineChart: LineChart
    private val bluetoothManager = BluetoothManager()

    // Define the request code for Bluetooth permissions
    private val REQUEST_BLUETOOTH_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        batteryLevelTextView = findViewById(R.id.batteryLevelTextView)
        wattageHarvestedTextView = findViewById(R.id.wattageHarvestedTextView)
        wattageConsumedTextView = findViewById(R.id.wattageConsumedTextView)
        lineChart = findViewById(R.id.lineChart)

        // Show login dialog first
        showLoginDialog()
    }

    private fun showLoginDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_login, null)
        builder.setView(view)

        val usernameInput: TextView = view.findViewById(R.id.usernameInput)
        val passwordInput: TextView = view.findViewById(R.id.passwordInput)

        builder.setPositiveButton("Login") { _, _ ->
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            if (username == "admin" && password == "password") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // After successful login, initialize the UI
                initializeUI()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                // Show the dialog again if login fails
                showLoginDialog()
            }
        }
        builder.setNegativeButton("Cancel") { _, _ -> finish() } // Exit app if canceled
        builder.show()
    }

    private fun initializeUI() {
        // Make the UI components visible after login
        batteryLevelTextView.visibility = View.VISIBLE
        wattageHarvestedTextView.visibility = View.VISIBLE
        wattageConsumedTextView.visibility = View.VISIBLE
        lineChart.visibility = View.VISIBLE // Make the chart visible

        // Request Bluetooth permissions and connect to Bluetooth device
        requestBluetoothPermissions()
    }

    private fun requestBluetoothPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_BLUETOOTH_PERMISSIONS // Fixed reference here
            )
        } else {
            connectToBluetoothDevice()
        }
    }

    private fun connectToBluetoothDevice() {
        val device: BluetoothDevice? = bluetoothManager.getPairedDevice()
        device?.let { bluetoothManager.connect(it) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BLUETOOTH_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                connectToBluetoothDevice()
            } else {
                Toast.makeText(this, "Bluetooth permissions are required to use this feature.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
