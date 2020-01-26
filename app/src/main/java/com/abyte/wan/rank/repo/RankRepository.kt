package com.abyte.wan.rank.repo

import com.abyte.core.utils.RxUtil
import com.abyte.wan.rank.api.RankApi
import com.abyte.wan.rank.model.RankPage
import io.reactivex.Observable

class RankRepository(private val api: RankApi) {

    fun getRankList(page: Int): Observable<RankPage> {
        return api.getRankList(page)
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }
}