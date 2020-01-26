package com.abyte.wan.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abyte.core.di.ViewModelFactory
import com.abyte.core.ext.hideSoftInput
import com.abyte.core.ext.log
import com.abyte.core.ext.yes
import com.abyte.wan.R
import com.abyte.wan.core.base.ui.BaseActivity
import com.abyte.wan.login.vm.LoginViewModel
import kotlinx.android.synthetic.main.app_bar_simple.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.toast
import javax.inject.Inject

class LoginActivity : BaseActivity() {


    private lateinit var loginViewModel: LoginViewModel


    @Inject
    lateinit var mFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        loginViewModel = ViewModelProviders.of(this, mFactory).get(LoginViewModel::class.java)

        observerData()

        btnSubmit.onClick {
            showLoading(true)
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()
            loginViewModel.checkUsernameAndPassword(
                username,
                password
            ).yes {
                hideSoftInput()
                loginViewModel.login(username, password)
            }
        }
    }

    private fun observerData() {
        loginViewModel.run {
            getLoginResult().observe(this@LoginActivity, Observer {
                toast("登录成功")
                showLoading(false)
                finish()
            })

            getErrorResult().observe(this@LoginActivity, Observer {
                log("errorCode = ${it.first}, errorMsg = ${it.second}")
                toast(it.second)
                showLoading(false)
            })
        }
    }

    private fun showLoading(show: Boolean) {
        loginContainer.animate()
            .setDuration(SHORT_ANIM_TIME)
            .alpha((if (show) 0 else 1).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    loginContainer.visibility = if (show) View.GONE else View.VISIBLE
                }
            })

        loginProgress.animate()
            .setDuration(SHORT_ANIM_TIME)
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    loginProgress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
    }

    companion object {

        const val SHORT_ANIM_TIME = 200L

        fun startLoginActivity(activity: BaseActivity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }
}