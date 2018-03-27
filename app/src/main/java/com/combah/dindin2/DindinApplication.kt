package com.combah.dindin2

import android.app.Application
import com.combah.dindin2.dependencyinjection.DaggerDindinComponent
import com.combah.dindin2.dependencyinjection.DindinComponent
import com.combah.dindin2.dependencyinjection.DindinModule

class DindinApplication : Application() {

    val component: DindinComponent by lazy {
        DaggerDindinComponent.builder()
                .dindinModule(DindinModule(this))
                .build()
    }
}