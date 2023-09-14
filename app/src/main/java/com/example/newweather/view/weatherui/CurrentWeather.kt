package com.example.newweather.view.weatherui

import com.example.newweather.model.remotedata.cities.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newweather.R
import com.example.newweather.databinding.Fragment2Binding
import com.example.newweather.view.adapters.SearchAdapter
import com.example.newweather.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurrentWeather : Fragment() {

    lateinit var binding: Fragment2Binding
    private val viewModel: WeatherViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@CurrentWeather).navigateUp();
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_2, container, false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchedcity.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.recyclerView.visibility = View.INVISIBLE
                    viewModel.getWeather(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                newText.let {
                    if(it.length>=3){
                        binding.recyclerView.visibility = View.VISIBLE
                        viewModel.getCities(it)
                    }
                }
                return true
            }
        })

        lifecycleScope.launch {
            viewModel.weather.collect {
                it.data?.let {
                    binding.location.text = it.name
                    Log.d("WeatherInfo", it.name.toString())
                    binding.weatherCondition.text =
                        "${it.weather[0].main}: ${it.weather[0].description}"
                    binding.degrees.text = "${it.main.temp}ºC"
                    binding.maxTemp.text = "${it.main.temp_max}ºC"
                    binding.minTemp.text = "${it.main.temp_min}ºC"
                    val iconId = it.weather[0].icon
                    val imgUrl = "https://openweathermap.org/img/w/${iconId}.png"
                    Picasso.get().load(imgUrl).into(binding.imageView)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.cities.collect({
                it.let {
                    searchRecyclerView(binding, viewModel, it)
                }
            })
        }

    }

    fun searchRecyclerView(binding:Fragment2Binding, WeatherViewModel: WeatherViewModel, data: ArrayList<Location>){
        this.binding.recycler.layoutManager = LinearLayoutManager(context)
        val adapter = SearchAdapter(binding,WeatherViewModel,data)
        this.binding.recycler.adapter = adapter
    }

}

