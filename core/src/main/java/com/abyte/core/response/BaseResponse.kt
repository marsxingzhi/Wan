package com.abyte.core.response

import com.abyte.core.anno.PoKo

@PoKo
data class BaseResponse<T>(
    var data: T,
    var errorCode: Int = 0,
    var errorMsg: String
)

const val SUCCESS = 0
const val ERROR = -1