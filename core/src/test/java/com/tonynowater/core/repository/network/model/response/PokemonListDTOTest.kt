package com.tonynowater.core.repository.network.model.response

import com.tonynowater.core.repository.database.entity.PokemonEntity
import org.junit.Assert
import org.junit.Test

class PokemonListDTOTest {
    @Test
    fun test() {
        val pokemon = PokemonEntity(name = "name", url = "https://pokeapi.co/api/v2/pokemon/2/")
        Assert.assertEquals("https://pokeres.bastionbot.org/images/pokemon/2.png", pokemon.getImageUrl())
    }

}
