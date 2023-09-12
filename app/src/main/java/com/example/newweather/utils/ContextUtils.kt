package com.example.newweather.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.newweather.utils.UserPermission.Companion.PERMISSION_REQUEST_ACCESS_LOCATION
import android.Manifest
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class UserPermission(){
    companion object{
        val PERMISSION_REQUEST_ACCESS_LOCATION = 200
    }
}

private fun isLocationEnabled(context: Context):Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.isLocationEnabled
    } else {
        val mode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF)
        mode != Settings.Secure.LOCATION_MODE_OFF
    }
}

private fun checkLocationPermissions(context: Context):Boolean{
    if(ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
        return true
    }
    return false
}

fun Context.checkPermission(): Boolean{
    return (isLocationEnabled(this) && checkLocationPermissions(this))
}

fun Context.requestPermission(){
    if (!isLocationEnabled(this)){
        Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        this.startActivity(intent)
    }
    if (!checkLocationPermissions(this)){
        ActivityCompat.requestPermissions(
            this as Activity,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }
}

fun Context.getCurrentLocation(getLocalWeather: (city: String)->Unit) {
    val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        return
    }
    fusedLocationProviderClient.lastLocation.addOnCompleteListener(this as Activity){ task ->
        val location = task.result
        val geocoder = Geocoder(this, Locale.getDefault())
        if(location==null){
            Toast.makeText(this,"Null Received", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Get Success", Toast.LENGTH_SHORT).show()
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
                geocoder.getFromLocation(location.latitude,location.longitude,1,object: Geocoder.GeocodeListener{
                    override fun onGeocode(address: MutableList<Address>) {
                        getLocalWeather(address[0].locality)
                    }
                })
            }else{
                getLocalWeather(geocoder.getFromLocation(location.latitude,location.longitude,1)?.get(0)?.locality.toString())
            }
        }
    }
}

