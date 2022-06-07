package com.example.tenunaraapplication.main.ui.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tenunaraapplication.R
import com.example.tenunaraapplication.databinding.ActivityFindTenunShopMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class FindTenunShopMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityFindTenunShopMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFindTenunShopMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        // Add a marker in Sydney and move the camera
        val tangerang = LatLng(-6.242484,106.626592)
        mMap.addMarker(MarkerOptions().position(tangerang).title("Marker in Summarecon"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tangerang))

        val tunjungBiruTenunPagringsingan = LatLng(-8.475362655634173, 115.56677156140496)
        mMap.addMarker(MarkerOptions().position(tunjungBiruTenunPagringsingan).title("Tunjung Biru Tenun Pagringsingan"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tunjungBiruTenunPagringsingan))

        val tenunLurikKembangan = LatLng(-7.777667808145942, 110.24185122658518)
        mMap.addMarker(MarkerOptions().position(tenunLurikKembangan).title("Tenun Lurik Kembangan"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tenunLurikKembangan))

        val wisnuArtShop = LatLng(-8.477174357344113, 115.56648137116464)
        mMap.addMarker(MarkerOptions().position(wisnuArtShop).title("Wisnu Art Shop"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(wisnuArtShop))

        val songketPusakoMinang = LatLng(-0.9454716519447142, 100.35993827116467)
        mMap.addMarker(MarkerOptions().position(songketPusakoMinang).title("Songket Pusako Minang"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(songketPusakoMinang))

        val putriAyuSongket = LatLng(-0.3886716353397287, 100.39089139999999)
        mMap.addMarker(MarkerOptions().position(putriAyuSongket).title("Putri Ayu Songket"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(putriAyuSongket))
    }

}