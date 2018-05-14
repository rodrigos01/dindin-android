package com.combah.dindin2.dependencyinjection

import android.arch.lifecycle.ViewModel
import com.combah.dindin2.viewmodel.MainViewModel
import com.combah.dindin2.viewmodel.MovementEditViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovementEditViewModel::class)
    abstract fun bindMovementEditViewModel(movementEditViewModel: MovementEditViewModel): ViewModel
}