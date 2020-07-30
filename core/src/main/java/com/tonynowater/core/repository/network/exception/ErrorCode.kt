package com.tonynowater.core.repository.network.exception

object ErrorCode {
    // Custom
    const val NO_NETWORK = "999_000"
    const val RESPONSE_JSON_ERROR = "888_001"
    const val SQL_ERROR = "888_003"
    const val ROOM_SCHEMA_CHANGED_ERROR = "888_004"

    // Common
    const val INVALID_PARAMETERS = "400_000"
    const val UNAUTHORIZED = "401_000"
    const val FORBIDDEN = "403_000"
    const val RESOURCE_NOT_FOUND = "404_000"
    const val UNEXPECTED_SERVER_ERROR = "500_000"
    const val SERVER_DOWN = "504_000"
}