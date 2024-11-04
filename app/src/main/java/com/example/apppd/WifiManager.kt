package com.example.apppd

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class WiFiManager {
    private val client = OkHttpClient()
    private val nodeMCUUrl = "http://192.168.4.1/data"  // Replace with NodeMCU's IP

    fun fetchData(): String? {
        val request = Request.Builder()
            .url(nodeMCUUrl)
            .build()

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        } catch (e: IOException) {
            null
        }
    }
}
