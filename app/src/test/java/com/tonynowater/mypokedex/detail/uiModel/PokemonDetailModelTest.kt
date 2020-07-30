package com.tonynowater.mypokedex.detail.uiModel

import org.junit.Assert
import org.junit.Test

class PokemonDetailModelTest {

    @Test
    fun test() {
        // Given
        val expected = "https://pokeres.bastionbot.org/images/pokemon/1.png"
        val model = PokemonDetailModel(
            name = "name",
            id = 1,
            type = emptyList(),
            weight = 0F,
            height = 0F,
            hp = 0,
            atk = 0,
            def = 0,
            spd = 0,
            exp = 0
        )

        // Action
        val imageUrl = model.getImageUrl()

        // Assert
        Assert.assertEquals(expected, imageUrl)
    }

}
