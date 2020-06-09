package com.example.lab9_gianfranco_traverso

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.lab9_gianfranco_traverso.utils.LocationUtil

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.lab9_gianfranco_traverso.model.GifDao
import com.example.lab9_gianfranco_traverso.model.Database
import com.example.lab9_gianfranco_traverso.model.Giflocation

class MapsActivity : AppCompatActivity() {

    lateinit var database: GifDao
    private lateinit var mMap: GoogleMap
    private lateinit var locationData: LocationUtil
    var coordinates = mutableListOf<LatLng>()
    var gifList = ArrayList<Giflocation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        database = Room.databaseBuilder(applicationContext, Database::class.java,"gif").build().gifDao()

        AsyncTask.execute(){
            val list = database.getAllGifs()
            for (gif in list){
                gifList.add(Giflocation(gif.longitude, gif.latitude, gif.title))
                println("Aqui la location del gif"+ gif.longitude.toString())
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        locationData = LocationUtil(this)
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            // Add a marker in Santiago and move the camera
            val santiago = LatLng(gifList[gifList.size-1].latitude, gifList[gifList.size-1].longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago, 13f))
        }
        invokeLocationAction()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private fun invokeLocationAction() {
        when {
            isPermissionsGranted() -> locationData.observe(this, Observer {

                coordinates.add(LatLng(it.latitude,it.longitude))

                for(gif in gifList){
                    val start = LatLng(gif.latitude, gif.longitude)
                    mMap.addMarker(MarkerOptions().position(start).title(gif.title))


                }
            })

            shouldShowRequestPermissionRationale() -> println("Ask Permission")

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION -> {
                invokeLocationAction()
            }
        }
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    companion object {
        var LOCATION_PERMISSION = 100
    }
}