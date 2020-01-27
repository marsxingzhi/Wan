package com.abyte.wan.knowledge.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abyte.core.exception.ApiErrorCode
import com.abyte.core.exception.ApiException
import com.abyte.core.rx.RxViewModel
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.knowledge.model.KnowledgeNavigationData
import com.abyte.wan.knowledge.repo.KnowledgeRepository

class KnowledgeViewModel(val repo: KnowledgeRepository) : RxViewModel() {

    private var chapterDataResult = MutableLiveData<List<ChapterData>>()

    private var knowledgeNaviDataResult = MutableLiveData<List<KnowledgeNavigationData>>()

    private var errorResult = MutableLiveData<Pair<Int, String>>()

    fun getChapterDataResult(): LiveData<List<ChapterData>> {
        return chapterDataResult
    }

    fun getKnowledgeNaviDataResult(): LiveData<List<KnowledgeNavigationData>> {
        return knowledgeNaviDataResult
    }

    fun getErrorResult(): LiveData<Pair<Int, String>> {
        return errorResult
    }

    fun getChapterList() {
        register(repo.getChapterList().subscribe({
            it?.let {
                chapterDataResult.value = it
            } ?: run {
                errorResult.value = Pair(ApiErrorCode.ERROR_DATA_EMPTY, "数据为空")
            }
        }, {
            val exception = it as ApiException
            errorResult.value = Pair(exception.code, exception.msg ?: "请求失败")
        }))
    }

    fun getKnowledgeNaviList() {
        register(repo.getKnowledgeNaviList().subscribe({
            it?.let {
                knowledgeNaviDataResult.value = it
            } ?: run {
                errorResult.value = Pair(ApiErrorCode.ERROR_DATA_EMPTY, "数据为空")
            }
        }, {
            val exception = it as ApiException
            errorResult.value = Pair(exception.code, exception.msg ?: "请求失败")
        }))
    }
}