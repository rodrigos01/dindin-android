package com.combah.dindin2.dependencyinjection

import com.combah.dindin2.view.MainActivity
import com.combah.dindin2.view.MovementEditActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(DindinModule::class), (ViewModelModule::class)])
@Singleton
interface DindinComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(movementEditActivity: MovementEditActivity)
}