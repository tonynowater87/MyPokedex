package com.tonynowater.core.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.tonynowater.core.repository.network.retrofit.API

@Entity
data class PokemonEntity(
    var page: Int = 0,
    @field:Json(name = "name")
    @PrimaryKey
    val name: String,
    @field:Json(name = "url")
    val url: String
) {
    fun getImageUrl(): String {
        return String.format(API.IMAGE_URL_FORMAT, url.split("/").dropLast(1).last())
    }
}