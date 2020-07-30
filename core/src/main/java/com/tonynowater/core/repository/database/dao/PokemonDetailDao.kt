package com.tonynowater.core.repository.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonynowater.core.repository.database.entity.PokemonDetailEntity
import io.reactivex.Single

@Dao
interface PokemonDetailDao: BaseDao<PokemonDetailEntity> {

    @Query("SELECT * FROM PokemonDetailEntity WHERE name = :name")
    fun getPokemonDetailByName(name: String): Single<PokemonDetailEntity>
}