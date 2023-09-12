package com.example.newweather.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newweather.R
import com.example.newweather.databinding.Fragment2Binding
import com.example.newweather.model.localdata.Location
import com.example.newweather.viewmodel.CustomViewModel

class SearchAdapter(
    private val binding: Fragment2Binding,
    private val viewModel: CustomViewModel,
    private val data: ArrayList<Location>
): RecyclerView.Adapter<SearchAdapter.ViewHolder> (){

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val searchList=view.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.search_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.searchList.text=data[position].city
        holder.searchList.setOnClickListener {
            viewModel.getWeather(data[position].city)
            binding.recyclerView.visibility = View.INVISIBLE
        }
    }

}