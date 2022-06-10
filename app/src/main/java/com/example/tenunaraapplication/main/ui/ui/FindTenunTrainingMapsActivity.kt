package com.example.tenunaraapplication.main.ui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.ActivityFindTenunTrainingMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FindTenunTrainingMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityFindTenunTrainingMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindTenunTrainingMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val multitenun = LatLng(-7.710291635874593, 110.70540333125328)
        mMap.addMarker(MarkerOptions().position(multitenun).title("CV Warisan Multi Tenun"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(multitenun))
    }
}