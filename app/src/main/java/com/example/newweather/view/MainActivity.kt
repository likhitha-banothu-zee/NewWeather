package com.example.newweather.view

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.newweather.R
import com.example.newweather.utils.UserPermission
import com.example.newweather.utils.requestPermission
import com.example.newweather.utils.checkPermission
import com.example.newweather.utils.getCurrentLocation
import com.example.newweather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<WeatherViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermission()){
            requestPermission()
        }
        getCurrentLocation{city ->
            Log.d("check",city)
            Log.d("activity","Got Successfully")
            viewModel.getWeather(city)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == UserPermission.PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getCurrentLocation{city ->
                    Toast.makeText(this,"Got Successfully", Toast.LENGTH_SHORT).show()
                    viewModel.getWeather(city)
                }
            }
            else{
                Toast.makeText(this,"Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}