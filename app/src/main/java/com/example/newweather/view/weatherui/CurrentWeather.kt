package com.example.newweather.view.weatherui

import com.example.newweather.model.localdata.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newweather.R
import com.example.newweather.databinding.Fragment2Binding
import com.example.newweather.view.adapters.SearchAdapter
import com.example.newweather.viewmodel.CustomViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CurrentWeather : Fragment() {

    lateinit var binding: Fragment2Binding
    private val viewModel: CustomViewModel by activityViewModel()

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
        lifecycleScope.launch {
            viewModel.data2.collect({
                it.let {
                    SearchRecyclerView(binding, viewModel, it)
                }
            })
        }

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
            viewModel.result1.collect{
                it.data?.let {
                    binding.location.text=it.name
                    binding.weatherCondition.text="${it.weather[0].main}: ${it.weather[0].description}"
                    binding.degrees.text="${it.main.temp}ºC"
                    binding.maxTemp.text="${it.main.temp_max}ºC"
                    binding.minTemp.text="${it.main.temp_min}ºC"
                    val iconId=it.weather[0].icon
                    val imgUrl="https://openweathermap.org/img/w/${iconId}.png"
                    Picasso.get().load(imgUrl).into(binding.imageView)
                }
            }
        }

        binding.search.setOnClickListener {
            viewModel.getForecast(binding.location.text.toString())
            Navigation.findNavController(it).navigate(R.id.action_fragment2_to_fragment3)
        }
    }

    fun SearchRecyclerView(binding:Fragment2Binding,viewModel: CustomViewModel, data: ArrayList<Location>){
        this.binding.recycler.layoutManager = LinearLayoutManager(context)
        val adapter = SearchAdapter(binding,viewModel,data)
        this.binding.recycler.adapter = adapter
    }

}

