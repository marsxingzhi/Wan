package com.abyte.wan.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abyte.core.exception.ApiErrorCode
import com.abyte.core.exception.ApiException
import com.abyte.core.ext.log
import com.abyte.core.rx.RxViewModel
import com.abyte.wan.main.model.ArticlePage
import com.abyte.wan.main.repo.MainRepository

class MainViewModel(private val mainRepo: MainRepository) : RxViewModel() {

    private var articlePageData = MutableLiveData<ArticlePage>()

    private var errorResult = MutableLiveData<Pair<Int, String>>()

    fun getArticlePages(): LiveData<ArticlePage> {
        return articlePageData
    }

    fun getErrorResult(): LiveData<Pair<Int, String>> {
        return errorResult
    }

    fun getArticlePages(pageIndex: Int) {
        log("MainViewModel----getArticlePages---pageIndex = $pageIndex")
        register(
            mainRepo.getArticlePages(pageIndex)
                .subscribe({
                    log("MainViewModel---getArticlePages---success--it = $it")
                    if (it != null) {
                        articlePageData.value = it
                    } else {
                        errorResult.value = Pair(ApiErrorCode.ERROR_DATA_EMPTY, "没有数据！")
                    }
                }, {
                    log("MainViewModel---getArticlePages---error--it = $it")
                    val exception = it as ApiException
                    errorResult.value = Pair(exception.code, exception.msg ?: "请求失败")
                })
        )
    }

    fun loadMoreData(currentPage: Int) {
        // 不用加1，初始请求page传0，返回的curPage为1，所以loadMore的时候无需加1
        getArticlePages(currentPage)
    }

    fun refreshData() {
        getArticlePages(0)
    }
}