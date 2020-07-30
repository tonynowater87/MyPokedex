package com.tonynowater.mypokedex.detail.uiModel

import com.tonynowater.core.repository.database.entity.PokemonDetailEntity
import com.tonynowater.core.repository.network.retrofit.API
import kotlin.random.Random

data class PokemonDetailModel(
    val name: String,
    val id: Int,
    val type: List<String>,
    val weight: Float,
    val height: Float,
    val hp: Int,
    val atk: Int,
    val def: Int,
    val spd: Int,
    val exp: Int
) {
    companion object {

        private const val MAX_VALUE = 100

        fun from(entity: PokemonDetailEntity): PokemonDetailModel {
            return PokemonDetailModel(
                name = entity.name,
                id = entity.id,
                type = entity.type,
                weight = entity.weight,
                height = entity.height,
                hp = entity.hp,
                atk = entity.atk,
                def = entity.def,
                spd = entity.spd,
                exp = entity.exp
            )
        }

        private fun generateRandomStat() = Random.nextInt(1, MAX_VALUE + 1)
    }

    fun getImageUrl(): String {
        return String.format(API.IMAGE_URL_FORMAT, id.toString())
    }
}