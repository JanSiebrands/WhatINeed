package de.siebrands.whatineed.di

import app.cash.sqldelight.db.SqlDriver
import de.siebrands.whatineed.db.DatabaseDriverFactory
import de.siebrands.whatineed.db.WhatINeed
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> { DatabaseDriverFactory().createDriver() }
    single<WhatINeed> { WhatINeed(get()) }
}