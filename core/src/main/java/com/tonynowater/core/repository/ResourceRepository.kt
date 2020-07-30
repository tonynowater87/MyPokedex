package com.tonynowater.core.repository

import com.tonynowater.core.repository.database.entity.PokemonDetailEntity
import com.tonynowater.core.repository.database.entity.PokemonEntity
import io.reactivex.Observable

interface ResourceRepository {
    fun getPokemonList(page: Int = 0): Observable<List<PokemonEntity>>
    fun getPokemonDetail(name: String): Observable<PokemonDetailEntity>
}