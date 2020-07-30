package com.tonynowater.core.repository.network

import com.tonynowater.core.repository.network.exception.ErrorState

class Resource<T> private constructor(
    val status: Status,
    private val data: T?,
    val errorState: ErrorState?,
    private val replay: Boolean = true
) {

    private var handled = false

    enum class Status { SUCCESS, ERROR }

    fun extract(): T? = if (!replay && handled) {
        null
    } else {
        handled = true
        data
    }

    fun extractErrorState(): ErrorState? = if (!replay && handled) {
        null
    } else {
        handled = true
        errorState
    }

    fun isError() = data == null

    companion object {

        fun <T> success(data: T, replay: Boolean = true): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                replay
            )
        }

        fun <T> error(msg: ErrorState, replay: Boolean = true): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                msg,
                replay
            )
        }
    }
}