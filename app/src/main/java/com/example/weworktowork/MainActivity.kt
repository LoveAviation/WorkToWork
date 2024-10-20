package com.example.weworktowork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.weworktowork.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener{
            WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork("TYT BbIJL R", ExistingPeriodicWorkPolicy.KEEP, PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES, 9, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build())
        }

        binding.button2.setOnClickListener{
            WorkManager.getInstance(this)
                .cancelUniqueWork("TYT BbIJL R")
        }
    }
}