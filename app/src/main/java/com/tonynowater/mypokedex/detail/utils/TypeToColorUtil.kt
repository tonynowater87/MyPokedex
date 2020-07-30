package com.tonynowater.mypokedex.detail.utils

import android.graphics.Color

// color reference : https://bulbapedia.bulbagarden.net/wiki/Category:Type_color_templates#Random
object TypeToColorUtil {
    fun typeToColor(type: String?): Int {
        return when(type) {
            "bug" -> Color.parseColor("#A8B820")
            "dark" -> Color.parseColor("#705848")
            "dragon" -> Color.parseColor("#7038F8")
            "electric" -> Color.parseColor("#F8D030")
            "fairy" -> Color.parseColor("#EE99AC")
            "fighting" -> Color.parseColor("#C03028")
            "fire" -> Color.parseColor("#F08030")
            "flying" -> Color.parseColor("#A890F0")
            "ghost" -> Color.parseColor("#705898")
            "grass" -> Color.parseColor("#78C850")
            "ground" -> Color.parseColor("#E0C068")
            "ice" -> Color.parseColor("#98D8D8")
            "normal" -> Color.parseColor("#A8A878")
            "poison" -> Color.parseColor("#A040A0")
            "psychic" -> Color.parseColor("#F85888")
            "rock" -> Color.parseColor("#B8A038")
            "steel" -> Color.parseColor("#B8B8D0")
            "water" -> Color.parseColor("#6890F0")
            "???" -> Color.parseColor("#68A090")
            else -> Color.TRANSPARENT
        }
    }
}