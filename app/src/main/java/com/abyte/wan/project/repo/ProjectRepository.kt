package com.abyte.wan.project.repo

import com.abyte.core.utils.RxUtil
import com.abyte.wan.knowledge.model.ChapterData
import com.abyte.wan.project.api.ProjectApi
import io.reactivex.Observable

class ProjectRepository(private val api: ProjectApi) {

    fun getProjectTabs(): Observable<List<ChapterData>> {
        return api.getProjectTabs()
            .compose(RxUtil.applySchedulers())
            .compose(RxUtil.handleResult())
    }
}