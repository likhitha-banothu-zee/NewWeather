package com.example.newweather.view.weatherui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newweather.R
import com.example.newweather.databinding.Fragment3Binding
import com.example.newweather.model.remotedata.forecast.DataForecast
import com.example.newweather.model.remotedata.forecast.Forecast
import com.example.newweather.model.repository.Repo
import com.example.newweather.view.adapters.ForecastAdapter
import com.example.newweather.viewmodel.CustomViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ForecastWeather : Fragment() {

    lateinit var binding: Fragment3Binding
    private val viewModel: CustomViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@ForecastWeather).navigateUp();
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_3, container, false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.result3.collect{
                it.data?.let {
                    recycler(it.list as ArrayList<DataForecast>)
                }
            }
        }

    }

    fun recycler(result: ArrayList<DataForecast>){
        binding.forecastbox.layoutManager=GridLayoutManager(context,3)
        binding.forecastbox.adapter=ForecastAdapter(binding,viewModel,result)
    }
}