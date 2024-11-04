package com.example.apppd

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class WattageGeneratedActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wattage_generated)

        lineChart = findViewById(R.id.lineChart)
        val backToDashboardButton: Button = findViewById(R.id.backToDashboardButton)

        // Simulate chart data (replace with real Bluetooth/Wi-Fi data)
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 20f))
        entries.add(Entry(2f, 30f))
        entries.add(Entry(3f, 10f))

        val dataSet = LineDataSet(entries, "Wattage Generated")
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()

        backToDashboardButton.setOnClickListener {
            finish()
        }
    }
}
