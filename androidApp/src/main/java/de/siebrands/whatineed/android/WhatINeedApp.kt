package de.siebrands.whatineed.android

import android.app.Application
import de.siebrands.whatineed.android.di.databaseModule
import de.siebrands.whatineed.android.di.viewModelsModule
import de.siebrands.whatineed.di.sharedKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WhatINeedApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKoinModules + viewModelsModule + databaseModule

        startKoin {
            androidContext(this@WhatINeedApp)
            modules(modules)
        }
    }
}