package com.example.sevenone

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer


class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var myLoc: UserLocationLayer
    private lateinit var obj: MapObjectCollection


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("cd553a06-0c0f-40a3-9dcf-f8048c81a632")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)
        getLoc()

        val compassBut: FloatingActionButton = findViewById(R.id.floatingActionButton)
        val toggleBut: ToggleButton = findViewById(R.id.toggleButton)
        val mapKit: MapKit = MapKitFactory.getInstance()
        val trafficJam = mapKit.createTrafficLayer(mapView.mapWindow)
        myLoc = mapKit.createUserLocationLayer(mapView.mapWindow)
        obj = mapView.map.mapObjects

        mapView.map.addInputListener(myList)

        compassBut.setOnClickListener {
            mapView.map.move(
                CameraPosition(
                    Point(55.3549067168026,86.08633428239138),17.0f, 0.0f, 0.0f,
                )
            )
            myLoc.isVisible = true
        }

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

    fun getLoc() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 0)
        return
    }

    fun addObj(point: Point){
        obj.addPlacemark(point)
    }
    val myList = object:InputListener {
        override fun onMapTap(map: Map, point: Point) {
            addObj(point)
            Log.i("dsf","sdlfkj")
        }

        override fun onMapLongTap(p0: Map, p1: Point) {
            Log.i("dsf","sdlfkj2")
        }
    }
}