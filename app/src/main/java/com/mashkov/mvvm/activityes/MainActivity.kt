package com.mashkov.mvvm.activityes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashkov.mvvm.databinding.ActivityMainBinding
import com.mashkov.mvvm.network.apis.GlobalApi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        GlobalApi.initialize()
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}