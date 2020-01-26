package com.abyte.core.exception

import com.google.gson.JsonParseException
import java.net.ConnectException

/**
 * 本地产生的异常
 */
object CustomException {

    private const val UNKNOWN = 1000

    private const val PARSE_ERROR = 1001

    private const val NETWORK_ERROR = 1002

    fun handleException(e: Throwable): ApiException {
        return when (e) {
            is JsonParseException -> ApiException(PARSE_ERROR, e.message)
            is ConnectException -> ApiException(NETWORK_ERROR, e.message)
            else -> ApiException(UNKNOWN, e.message)
        }
    }
}