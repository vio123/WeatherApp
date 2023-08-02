package com.example.weatherapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null


    private val weatherViewModel: WeatherViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        weatherViewModel.getWeather(44.6135,27.5645)
        weatherViewModel.weatherData.observe(this){
            binding?.weather = it
            Toast.makeText(this,it.temperature.toString(),Toast.LENGTH_SHORT).show()
        }
        /*
        viewModel?.getWeather(44.6135,27.5645)
        viewModel?.weatherData?.observe(this){
            binding?.weather = it
            Toast.makeText(this,it.temperature.toString(),Toast.LENGTH_SHORT).show()
        }
        viewModel?.error?.observe(this){
            Log.e("test123",it)
        }

         */
    }
}