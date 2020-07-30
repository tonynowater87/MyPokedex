package com.tonynowater.core.repository.network.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailDTO(
    @Json(name = "abilities")
    val abilities: List<Ability>,
    @Json(name = "base_experience")
    val baseExperience: Int,
    @Json(name = "forms")
    val forms: List<Form>,
    @Json(name = "game_indices")
    val gameIndices: List<GameIndice>,
    @Json(name = "height")
    val height: Int,
    @Json(name = "held_items")
    val heldItems: List<HeldItem>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String,
    @Json(name = "moves")
    val moves: List<Move>,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "species")
    val species: Species,
    @Json(name = "stats")
    val stats: List<Stat>,
    @Json(name = "types")
    val types: List<Type>,
    @Json(name = "weight")
    val weight: Int
) {
    @JsonClass(generateAdapter = true)
    data class Ability(
        @Json(name = "ability")
        val ability: Ability,
        @Json(name = "is_hidden")
        val isHidden: Boolean,
        @Json(name = "slot")
        val slot: Int
    ) {
        @JsonClass(generateAdapter = true)
        data class Ability(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Form(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )

    @JsonClass(generateAdapter = true)
    data class GameIndice(
        @Json(name = "game_index")
        val gameIndex: Int,
        @Json(name = "version")
        val version: Version
    ) {
        @JsonClass(generateAdapter = true)
        data class Version(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class HeldItem(
        @Json(name = "item")
        val item: Item,
        @Json(name = "version_details")
        val versionDetails: List<VersionDetail>
    ) {
        @JsonClass(generateAdapter = true)
        data class Item(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )

        @JsonClass(generateAdapter = true)
        data class VersionDetail(
            @Json(name = "rarity")
            val rarity: Int,
            @Json(name = "version")
            val version: Version
        ) {
            @JsonClass(generateAdapter = true)
            data class Version(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Move(
        @Json(name = "move")
        val move: Move,
        @Json(name = "version_group_details")
        val versionGroupDetails: List<VersionGroupDetail>
    ) {
        @JsonClass(generateAdapter = true)
        data class Move(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )

        @JsonClass(generateAdapter = true)
        data class VersionGroupDetail(
            @Json(name = "level_learned_at")
            val levelLearnedAt: Int,
            @Json(name = "move_learn_method")
            val moveLearnMethod: MoveLearnMethod,
            @Json(name = "version_group")
            val versionGroup: VersionGroup
        ) {
            @JsonClass(generateAdapter = true)
            data class MoveLearnMethod(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )

            @JsonClass(generateAdapter = true)
            data class VersionGroup(
                @Json(name = "name")
                val name: String,
                @Json(name = "url")
                val url: String
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class Species(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )

    @JsonClass(generateAdapter = true)
    data class Stat(
        @Json(name = "base_stat")
        val baseStat: Int,
        @Json(name = "effort")
        val effort: Int,
        @Json(name = "stat")
        val stat: Stat
    ) {
        @JsonClass(generateAdapter = true)
        data class Stat(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Type(
        @Json(name = "slot")
        val slot: Int,
        @Json(name = "type")
        val type: Type
    ) {
        @JsonClass(generateAdapter = true)
        data class Type(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }
}