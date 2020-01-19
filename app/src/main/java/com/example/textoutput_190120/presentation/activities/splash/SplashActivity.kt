package com.example.textoutput_190120.presentation.activities.splash

import android.os.Handler
import com.example.textoutput_190120.R
import com.example.textoutput_190120.di.component.ViewModelComponent
import com.example.textoutput_190120.presentation.base.BaseActivity
import com.example.textoutput_190120.utils.DELAY_3000
import com.example.textoutput_190120.databinding.SplashBinding


class SplashActivity : BaseActivity<SplashBinding>() {

    override fun injectDependency(component: ViewModelComponent) {
       component.inject(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun setupViewLogic(binding: SplashBinding) {
        android.os.Handler().postDelayed({
            navigator.openMainScreen()
            finish()
        }, DELAY_3000)
    }
}
