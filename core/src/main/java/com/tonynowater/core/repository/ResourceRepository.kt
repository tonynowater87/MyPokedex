package com.tonynowater.core.repository

import com.tonynowater.core.repository.database.entity.PokemonDetailEntity
import com.tonynowater.core.repository.database.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface ResourceRepository {
    suspend fun getPokemonList(page: Int = 0): Flow<List<PokemonEntity>>
    suspend fun getPokemonDetail(name: String): Flow<PokemonDetailEntity>
}