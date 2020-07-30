package com.tonynowater.core.repository.network.exception

interface ErrorHandler {

    fun extractErrorState(throwable: Throwable): ErrorState
}