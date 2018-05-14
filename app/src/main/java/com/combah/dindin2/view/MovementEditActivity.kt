package com.combah.dindin2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.combah.dindin2.DindinApplication
import com.combah.dindin2.R
import com.combah.dindin2.databinding.ActivityMovementBinding
import com.combah.dindin2.viewmodel.MovementEditViewModel
import com.combah.dindin2.viewmodel.ViewModelFactory
import javax.inject.Inject

class MovementEditActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as DindinApplication).component.inject(this)

        val binding = DataBindingUtil
                .setContentView<ActivityMovementBinding>(this, R.layout.activity_movement)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MovementEditViewModel::class.java)

        binding.viewModel = viewModel

        binding.saveButton.setOnClickListener {
            viewModel.save()
            finish()
        }

    }
}