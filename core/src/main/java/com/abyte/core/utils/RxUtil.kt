package com.abyte.core.utils

import com.abyte.core.exception.ApiException
import com.abyte.core.exception.CustomException
import com.abyte.core.response.BaseResponse
import com.abyte.core.response.SUCCESS
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function


object RxUtil {

    // 线程切换
    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 将BaseResponse<T> 转换成 T
     * 先检查是否是本地异常，例如解析错误，网络错误等
     * onErrorResumeNet：如果发送的Observable遇到错误，会新建Observable替换源Observable
     */
    fun <T> handleResult(): ObservableTransformer<BaseResponse<T>, T> {
        return ObservableTransformer {
            it.onErrorResumeNext(Function { throwable ->
                return@Function Observable.error(CustomException.handleException(throwable))
            }).flatMap(ResponseFunction())
        }
    }


    private class ResponseFunction<T> : Function<BaseResponse<T>, ObservableSource<T>> {

        @Throws(Exception::class)
        override fun apply(response: BaseResponse<T>): ObservableSource<T> {
            val code = response.errorCode
            val errorMsg = response.errorMsg
            return if (code == SUCCESS) {
                createData(response.data)
            } else {
                Observable.error(ApiException(code, errorMsg))
            }
        }

        private fun <T> createData(t: T): Observable<T> {
            return Observable.create { emitter ->
                try {
                    emitter.onNext(t)
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }
        }
    }
}