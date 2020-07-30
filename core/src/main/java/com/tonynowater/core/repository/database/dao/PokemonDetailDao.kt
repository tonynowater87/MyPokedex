package com.tonynowater.core.repository.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonynowater.core.repository.database.entity.PokemonDetailEntity

@Dao
interface PokemonDetailDao: BaseDao<PokemonDetailEntity> {

    @Query("SELECT * FROM PokemonDetailEntity WHERE name = :name")
    suspend fun getPokemonDetailByName(name: String): PokemonDetailEntity?
}