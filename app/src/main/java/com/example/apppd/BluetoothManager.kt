package com.example.apppd

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothManager(private val context: Context) {

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private lateinit var inputStream: InputStream
    private lateinit var outputStream: OutputStream

    fun connectToDevice(macAddress: String): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, return false or handle appropriately
            return false
        }

        return try {
            val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(macAddress)
            bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("YOUR_UUID"))

            bluetoothSocket?.let {
                it.connect()
                inputStream = it.inputStream
                outputStream = it.outputStream
            }
            true  // Successfully connected
        } catch (e: SecurityException) {
            e.printStackTrace()
            false  // SecurityException due to lack of permissions
        } catch (e: Exception) {
            e.printStackTrace()
            false  // Any other exception during connection
        }
    }

    fun disconnect() {
        try {
            bluetoothSocket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun receiveData(): String {
        return try {
            val buffer = ByteArray(1024)
            val bytes = inputStream.read(buffer)
            String(buffer, 0, bytes)
        } catch (e: Exception) {
            e.printStackTrace()
            ""  // Return an empty string if there's an error
        }
    }
}
