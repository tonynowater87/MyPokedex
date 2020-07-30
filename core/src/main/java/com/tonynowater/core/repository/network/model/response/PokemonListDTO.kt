package com.tonynowater.core.repository.network.model.response


import com.squareup.moshi.Json
import com.tonynowater.core.repository.database.entity.PokemonEntity

data class PokemonListDTO(
    @field:Json(name = "count")
    val count: Int,
    @field:Json(name = "next")
    val next: String,
    @field:Json(name = "previous")
    val previous: String? = null,
    @field:Json(name = "results")
    val results: List<PokemonEntity>
)