package com.abyte.core.exception

data class ApiException(val code: Int, val msg: String?) : Exception()