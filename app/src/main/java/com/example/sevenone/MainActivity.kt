package com.example.sevenone

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView


class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("cd553a06-0c0f-40a3-9dcf-f8048c81a632")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)

        var compassBut: FloatingActionButton = findViewById(R.id.floatingActionButton)
        compassBut.setOnClickListener {
            mapView.map.move(
                CameraPosition(
                    Point(55.3549067168026,86.08633428239138),17.0f, 0.0f, 0.0f,
                )
            )
        }

        var toggleBut: ToggleButton = findViewById(R.id.toggleButton)
        var mapKit: MapKit = MapKitFactory.getInstance()
        var trafficJam = mapKit.createTrafficLayer(mapView.mapWindow)
        toggleBut.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                trafficJam.isTrafficVisible = true
            } else {
                trafficJam.isTrafficVisible = false
            }
        })
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}