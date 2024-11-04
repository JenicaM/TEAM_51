package com.example.apppd

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class WattageConsumedActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var backToDashboardButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wattage_consumed)

        // Initialize views
        lineChart = findViewById(R.id.consumedChart)
        backToDashboardButton = findViewById(R.id.backToDashboardButton)

        // Simulate chart data (replace with real Bluetooth/Wi-Fi data in production)
        val entries = ArrayList<Entry>().apply {
            add(Entry(1f, 15f))
            add(Entry(2f, 25f))
            add(Entry(3f, 5f))
        }

        // Configure chart data
        val dataSet = LineDataSet(entries, "Wattage Consumed").apply {
            color = resources.getColor(R.color.teal_700, theme)
            setCircleColor(resources.getColor(R.color.teal_700, theme))
        }
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate() // Refresh chart to display data

        // Set button click listener to go back to the Dashboard
        backToDashboardButton.setOnClickListener {
            finish()
        }
    }
}
