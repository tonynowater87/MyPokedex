package com.tonynowater.core.repository.database.converter

import junit.framework.Assert
import org.junit.Test

class StringListConverterTest {

    @Test
    fun testToJsonString() {
        // Given
        val typeNameList = listOf("Fire", "Water")

        // Action
        val result = StringListConverter().toJsonString(typeNameList)

        // Assert
        Assert.assertEquals("[\"Fire\",\"Water\"]", result)
    }

    @Test
    fun testToStringList() {
        val jsonString = "[\"Fire\",\"Water\"]"

        val result = StringListConverter().toStringList(jsonString)

        Assert.assertEquals(listOf("Fire", "Water"), result)
    }
}
