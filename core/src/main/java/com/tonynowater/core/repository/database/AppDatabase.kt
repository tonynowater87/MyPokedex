package com.tonynowater.core.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tonynowater.core.repository.database.converter.StringListConverter
import com.tonynowater.core.repository.database.dao.PokemonDao
import com.tonynowater.core.repository.database.dao.PokemonDetailDao
import com.tonynowater.core.repository.database.entity.PokemonEntity
import com.tonynowater.core.repository.database.entity.PokemonDetailEntity

@Database(entities = [PokemonEntity::class, PokemonDetailEntity::class], version = 2, exportSchema = false)
@TypeConverters(value = [StringListConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
    abstract fun getPokemonDetailDao(): PokemonDetailDao
}