package com.example.sampleapppd

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class BluetoothManager(private val context: Context) {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null

    @SuppressLint("MissingPermission")
    fun getPairedDevice(): BluetoothDevice? {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        return pairedDevices?.firstOrNull() // Select a specific device if needed
    }

    fun connect(device: BluetoothDevice) {
        val uuid: UUID = UUID.fromString("YOUR_UUID_HERE") // Replace with your UUID
        try {
            if (context.checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                bluetoothSocket?.connect()
            } else {
                Log.e("BluetoothManager", "Bluetooth permission not granted")
            }
        } catch (e: IOException) {
            Log.e("BluetoothManager", "Error connecting to device", e)
        } catch (e: SecurityException) {
            Log.e("BluetoothManager", "Security Exception: Permission might be denied", e)
        }
    }

    fun readData(): String? {
        return try {
            if (bluetoothSocket?.isConnected == true) {
                val inputStream = bluetoothSocket!!.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                reader.readLine() // Read data from the Bluetooth device
            } else {
                Log.e("BluetoothManager", "Socket not connected")
                null
            }
        } catch (e: IOException) {
            Log.e("BluetoothManager", "Error reading data", e)
            null
        }
    }

    fun disconnect() {
        try {
            bluetoothSocket?.close()
        } catch (e: IOException) {
            Log.e("BluetoothManager", "Error disconnecting", e)
        }
    }
}
