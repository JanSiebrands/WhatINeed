package de.siebrands.whatineed.android.di

import app.cash.sqldelight.db.SqlDriver
import de.siebrands.whatineed.db.DatabaseDriverFactory
import de.siebrands.whatineed.db.WhatINeedDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> { DatabaseDriverFactory(androidContext()).createDriver() }
    single<WhatINeedDatabase> { WhatINeedDatabase(get()) }
}