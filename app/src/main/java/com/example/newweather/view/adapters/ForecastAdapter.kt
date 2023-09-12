package com.example.newweather.view.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.newweather.R
import com.example.newweather.databinding.Fragment3Binding
import com.example.newweather.model.remotedata.forecast.DataForecast
import com.example.newweather.viewmodel.CustomViewModel
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForecastAdapter(
    private val binding: Fragment3Binding,
    private val viewModel: CustomViewModel,
    private val data: ArrayList<DataForecast>
): RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val day=view.findViewById<TextView>(R.id.day)
        val temp=view.findViewById<TextView>(R.id.temp)
        val img=view.findViewById<ImageView>(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_arrangement,parent,false)
        return ForecastAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.day.text=displayTime(data[position].dt_txt)
        holder.temp.text="${data[position].main.temp.toInt()}ºC"
        Picasso.get()
            .load("https://openweathermap.org/img/w/${data[position].weather[0].icon}.png")
            .into(holder.img)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayTime(dtTxt: String): CharSequence? {
        val input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val output = DateTimeFormatter.ofPattern("MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(dtTxt,input)
        return output.format(dateTime)

    }

}

