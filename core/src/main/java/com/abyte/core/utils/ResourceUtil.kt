package com.abyte.core.utils

import android.content.res.Resources
import android.util.TypedValue

class ResourceUtil {


    companion object {


        private const val DP_05 = 0.5f

        fun dp2Px(dp: Float): Int {
            return (dp * Resources.getSystem().displayMetrics.density + DP_05).toInt()
        }

        fun sp2px(sp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics)
        }
    }
}