package com.abyte.wan.wechat.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abyte.core.exception.ApiErrorCode
import com.abyte.core.exception.ApiException
import com.abyte.core.rx.RxViewModel
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.wechat.repo.WechatRepository

class WechatViewModel(private val repo: WechatRepository) : RxViewModel() {


    private var wechatArticlesResult = MutableLiveData<List<ChapterData>>()

    private var errorResult = MutableLiveData<Pair<Int, String>>()

    fun getWechatArticlesResult(): LiveData<List<ChapterData>> {
        return wechatArticlesResult
    }

    fun getErrorResult(): LiveData<Pair<Int, String>> {
        return errorResult
    }

    fun getWechatArticles() {
        register(repo.getWechatArticles().subscribe({
            it?.let {
                wechatArticlesResult.value = it
            } ?: run {
                errorResult.value = Pair(ApiErrorCode.ERROR_DATA_EMPTY, "数据为空")
            }
        }, {
            val exception = it as ApiException
            errorResult.value = Pair(exception.code, exception.msg ?: "请求失败")
        }))
    }


}