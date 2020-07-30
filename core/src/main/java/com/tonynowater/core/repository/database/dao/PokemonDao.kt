package com.tonynowater.core.repository.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonynowater.core.repository.database.entity.PokemonEntity

@Dao
interface PokemonDao: BaseDao<PokemonEntity> {

    @Query("SELECT * FROM PokemonEntity WHERE page = :page")
    suspend fun getPokemonsByPage(page: Int): List<PokemonEntity>
}