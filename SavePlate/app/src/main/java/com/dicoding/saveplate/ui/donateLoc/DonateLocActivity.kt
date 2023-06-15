package com.dicoding.saveplate.ui.donateLoc

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.saveplate.R
import com.dicoding.saveplate.databinding.ActivityDonateLocBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLngBounds

class DonateLocActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDonateLocBinding
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDonateLocBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        supportActionBar?.apply {
            title = getString(R.string.donateloc)
        }

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#44746D")))

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        locationMarker()
    }

    private fun locationMarker(){
        val latLng_1 = LatLng(-6.281533749414377, 106.63659703048009)
        val latLng_2 = LatLng(-6.56717804961855, 106.85501445680853)
        val latLng_3 = LatLng(-6.364165823582509, 106.95898318386716)
        val latLng_4 = LatLng(-6.252020283919863, 106.97866345275067)
        val latLng_5 = LatLng(-6.1708017989806345, 106.95312308555087)
        val latLng_6 = LatLng(-6.2193904247553355, 106.66318967069375)
        val latLng_7 = LatLng(-6.189022602415335, 106.73399515589026)
        val latLng_8 = LatLng(-6.178529337060354, 106.79274154169194)
        val latLng_9 = LatLng(-6.176370897890243, 106.81690785123061)
        val latLng_10 = LatLng(-6.212876311005508, 106.83103101588613)
        val latLng_11 = LatLng(-6.207352488248963, 106.86210404076158)
        val latLng_12 = LatLng(-6.251901606077982, 106.80491893940783)
        val latLng_13 = LatLng(-6.26490396479736, 106.82100512596843)
        val latLng_14 = LatLng(-6.299719642315109, 106.77786349940578)


        mMap.addMarker(MarkerOptions().position(latLng_1).title("Prasetiya Mulya BSD"))
        mMap.addMarker(MarkerOptions().position(latLng_2).title("IKEA Sentul"))
        mMap.addMarker(MarkerOptions().position(latLng_3).title("Hero Kota Wisata"))
        mMap.addMarker(MarkerOptions().position(latLng_4).title("Hero Kamala Lagoon"))
        mMap.addMarker(MarkerOptions().position(latLng_5).title("IKEA JGC"))
        mMap.addMarker(MarkerOptions().position(latLng_6).title("IKEA Alam Sutera"))
        mMap.addMarker(MarkerOptions().position(latLng_7).title("Hero Puri Indah"))
        mMap.addMarker(MarkerOptions().position(latLng_8).title("Hero Taman Anggrek"))
        mMap.addMarker(MarkerOptions().position(latLng_9).title("Aswata Tanah Abang"))
        mMap.addMarker(MarkerOptions().position(latLng_10).title("Gedung Aswata"))
        mMap.addMarker(MarkerOptions().position(latLng_11).title("FoodCycle Hub"))
        mMap.addMarker(MarkerOptions().position(latLng_12).title("The Residence at Dharmawangsa"))
        mMap.addMarker(MarkerOptions().position(latLng_13).title("Hero Kemang"))
        mMap.addMarker(MarkerOptions().position(latLng_14).title("Hero Lebak Bulus"))

        boundsBuilder.include(latLng_1)
        boundsBuilder.include(latLng_2)
        boundsBuilder.include(latLng_3)
        boundsBuilder.include(latLng_4)
        boundsBuilder.include(latLng_5)
        boundsBuilder.include(latLng_6)
        boundsBuilder.include(latLng_7)
        boundsBuilder.include(latLng_8)
        boundsBuilder.include(latLng_9)
        boundsBuilder.include(latLng_10)
        boundsBuilder.include(latLng_11)
        boundsBuilder.include(latLng_12)
        boundsBuilder.include(latLng_13)
        boundsBuilder.include(latLng_14)

        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }
}