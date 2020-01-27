package com.abyte.wan.knowledge

import com.abyte.core.ext.log
import com.abyte.wan.core.base.model.FragmentPage
import com.abyte.wan.core.base.ui.CommonViewPageFragment

class KnowledgeFragment : CommonViewPageFragment() {

    override fun getFragmentPages(): List<FragmentPage> {
        log("KnowledgeFragment---getFragmentPages")
        return listOf(
            FragmentPage(SystemTreeFragment(), "体系"),
            FragmentPage(KnowledgeNavigationFragment(), "导航")
        )
    }
}