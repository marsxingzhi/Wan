//package com.abyte.wan.login
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import com.abyte.core.ext.log
//import com.abyte.wan.login.model.LoginInfo
//import com.abyte.wan.login.model.LoginType
//
//class LoginServiceImpl : ILoginService {
//
//    override fun login() {
//        log("LoginServiceImpl-login")
//    }
//
//    override fun login(activity: FragmentActivity, loginInfo: LoginInfo, callback: ILoginService.Callback
//    ) {
//        when (loginInfo.loginType) {
//            LoginType.ACCOUNT -> {
//                showLoginFragment(activity)
//            }
//            LoginType.MOBILE -> {
//            }
//            LoginType.WX -> {
//            }
//            LoginType.QQ -> {
//            }
//        }
//
//    }
//
//    private fun showLoginFragment(activity: FragmentActivity) {
//        LoginFragment.newInstance()
//            .show(activity.supportFragmentManager, "login")
//    }
//
//    override fun login(fragment: Fragment, loginInfo: LoginInfo, callback: ILoginService.Callback) {
//
//    }
//
//
//    fun test() {
//
//    }
//}