package com.tonynowater.core.repository.database

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "mypokedex-local-db-name")
            .fallbackToDestructiveMigration()
            .build()
    }
}