package com.tonynowater.core.repository.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.tonynowater.core.repository.database.entity.PokemonEntity
import io.reactivex.Single

@Dao
interface PokemonDao: BaseDao<PokemonEntity> {

    @Query("SELECT * FROM PokemonEntity WHERE page = :page")
    fun getPokemonsByPage(page: Int): Single<List<PokemonEntity>>
}