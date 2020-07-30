package com.tonynowater.core.repository.network.retrofit

import com.tonynowater.core.repository.network.model.response.PokemonDetailDTO
import com.tonynowater.core.repository.network.model.response.PokemonListDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    companion object {
        const val IMAGE_URL_FORMAT = "https://pokeres.bastionbot.org/images/pokemon/%s.png"
    }

    @GET("pokemon")
    fun getPokemonList(
        @Query(value = "limit") limit: Int = 10,
        @Query(value = "offset") offset: Int = 0
    ): Observable<PokemonListDTO>

    @GET("pokemon/{name}")
    fun getPokemonDetail(@Path("name") name: String): Observable<PokemonDetailDTO>
}