package com.tonynowater.core.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tonynowater.core.repository.network.model.response.PokemonDetailDTO
import kotlin.random.Random

@Entity
data class PokemonDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
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

        fun from(dto: PokemonDetailDTO): PokemonDetailEntity {
            return PokemonDetailEntity(
                name = dto.name,
                id = dto.id,
                type = dto.types.map { it.type.name },
                weight = dto.weight / 10F,
                height = dto.height / 10F,
                hp = generateRandomStat(),
                atk = generateRandomStat(),
                def = generateRandomStat(),
                spd = generateRandomStat(),
                exp = generateRandomStat()
            )
        }

        private fun generateRandomStat() = Random.nextInt(1, MAX_VALUE + 1)
    }
}