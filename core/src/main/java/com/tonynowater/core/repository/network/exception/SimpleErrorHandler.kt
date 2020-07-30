package com.tonynowater.core.repository.network.exception

import com.tonynowater.core.repository.network.HttpCode
import com.tonynowater.core.repository.network.monitor.NetworkStateMonitor
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.sql.SQLException

class SimpleErrorHandler(
    private val networkStateMonitor: NetworkStateMonitor
) : ErrorHandler {

    override fun extractErrorState(throwable: Throwable): ErrorState {

        Timber.w("[DEBUG] extractErrorState:$throwable")

        return when {
            throwable is IllegalStateException -> {
                createState(
                    HttpCode.APP,
                    ErrorCode.ROOM_SCHEMA_CHANGED_ERROR
                )
            }
            throwable is HttpException -> { // common error from server
                val responseBody = throwable.response()?.errorBody()
                extractFromBody(
                    if (throwable.response() == null) {
                        HttpCode.UNKNOWN
                    } else {
                        throwable.response()!!.code()
                    }, responseBody
                )
            }
            throwable is SQLException -> { // database related error
                createState(
                    HttpCode.APP,
                    ErrorCode.SQL_ERROR
                )
            }
            throwable is SocketTimeoutException || throwable is ConnectException && networkStateMonitor.hasNetwork() -> { // connection error
                createState(
                    HttpCode.GATEWAY_TIMEOUT,
                    ErrorCode.SERVER_DOWN
                )
            }
            throwable is IOException && !networkStateMonitor.hasNetwork() -> { // no network
                createState(
                    HttpCode.NO_CONNECTION,
                    ErrorCode.NO_NETWORK)
            }
            throwable is IOException && networkStateMonitor.hasNetwork() -> { // json parse error
                createState(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    ErrorCode.RESPONSE_JSON_ERROR
                )
            }
            else -> { // unexpected error
                createState(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    ErrorCode.UNEXPECTED_SERVER_ERROR
                )
            }
        }
    }


    private fun extractFromBody(httpCode: Int, responseBody: ResponseBody?): ErrorState {

        return try {

            //TODO: handle error response and create error state

             // if no specific error and common error found, handle it according to the http code
                when (httpCode) {
                    HttpCode.BAD_REQUEST -> createState(
                        HttpCode.BAD_REQUEST,
                        ErrorCode.INVALID_PARAMETERS
                    )
                    HttpCode.UNAUTHORIZED -> createState(
                        HttpCode.UNAUTHORIZED,
                        ErrorCode.UNAUTHORIZED
                    )
                    HttpCode.NOT_FOUND -> createState(
                        HttpCode.NOT_FOUND,
                        ErrorCode.RESOURCE_NOT_FOUND
                    )
                    HttpCode.FORBIDDEN -> createState(
                        HttpCode.FORBIDDEN,
                        ErrorCode.FORBIDDEN
                    )
                    HttpCode.GATEWAY_TIMEOUT -> createState(
                        HttpCode.GATEWAY_TIMEOUT,
                        ErrorCode.SERVER_DOWN
                    )
                    else -> createState(
                        HttpCode.INTERNAL_SERVER_ERROR,
                        ErrorCode.UNEXPECTED_SERVER_ERROR
                    )
                }

        } catch (e: Exception) {

            createState(
                HttpCode.INTERNAL_SERVER_ERROR,
                ErrorCode.RESPONSE_JSON_ERROR
            )
        }
    }

    private fun createState(
        httpCode: Int,
        errorCode: String,
        errorMessage: String? = null
    ): ErrorState =
        ErrorState(httpCode = httpCode, errorCode = errorCode, errorMessage = errorMessage)
}