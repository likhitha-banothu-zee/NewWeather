package com.example.newweather.view.weatherui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.newweather.R
import com.example.newweather.databinding.Fragment1Binding

class HomePage : Fragment() {

    lateinit var binding: Fragment1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_1, container, false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.screen.setOnClickListener {
            Log.d("first fragment","Got Successfully")
            Navigation.findNavController(view).navigate(R.id.action_fragment1_to_fragment2)
        }
    }

}