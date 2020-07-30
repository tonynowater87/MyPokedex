package com.tonynowater.core.repository.network.exception

data class ErrorState(val httpCode: Int, val errorCode: String, val errorMessage: String? = null) {
    fun hasErrorMessage(): Boolean = !errorMessage.isNullOrEmpty()
}