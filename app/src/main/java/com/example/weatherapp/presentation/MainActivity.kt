package com.example.weatherapp.presentation

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.common.DataState
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val weatherViewModel: WeatherViewModel by viewModels()
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        weatherViewModel.getWeather(44.6135, 27.5645)
        weatherViewModel.weatherViewState.observe(this) { state ->
            when (state) {
                is DataState.Loading -> showLoading()
                is DataState.Success -> {
                    binding?.weather = state.data
                    progressDialog?.dismiss()
                }
                else -> {
                    progressDialog?.dismiss()
                }
            }
        }
    }

    private fun showLoading() {
        Log.e("test123", "Loading")
        // Afișează mesajul de încărcare sau animația corespunzătoare
        progressDialog = ProgressDialog(this)
        progressDialog?.apply {
            setMessage("Încărcare...")
            setCancelable(true)
            show()
        }
    }

    override fun onDestroy() {
        weatherViewModel.stopWeatherUpdates()
        super.onDestroy()
    }
}