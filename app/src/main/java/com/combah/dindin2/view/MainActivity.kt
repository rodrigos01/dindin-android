package com.combah.dindin2.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.combah.dindin2.DindinApplication
import com.combah.dindin2.R
import com.combah.dindin2.databinding.ActivityMainBinding
import com.combah.dindin2.viewmodel.MainViewModel
import com.combah.dindin2.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as DindinApplication).component.inject(this)

        val binding = DataBindingUtil
                .setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.setLifecycleOwner(this)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        binding.previous.setOnClickListener { viewModel.previousPeriod() }
        binding.next.setOnClickListener { viewModel.nextPeriod() }

        binding.movementList.adapter = MovementsAdapter(this, viewModel.movements)

        binding.fab.setOnClickListener { launchMovementActivity() }

    }

    private fun launchMovementActivity() {
        startActivity(Intent(this, MovementEditActivity::class.java))
    }
}
