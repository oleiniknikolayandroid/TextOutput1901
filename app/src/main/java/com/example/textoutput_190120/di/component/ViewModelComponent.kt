package com.example.textoutput_190120.di.component


import com.example.textoutput_190120.di.module.ViewModelModule
import com.example.textoutput_190120.di.scope.ViewModelScope
import com.example.textoutput_190120.presentation.activities.main.MainActivity
import com.example.textoutput_190120.presentation.activities.splash.SplashActivity
import com.example.textoutput_190120.presentation.fragments.repositories.RepositoriesFragment
import dagger.Component

@ViewModelScope
@Component(modules = [ViewModelModule::class], dependencies = [InteractorComponent::class])
interface ViewModelComponent {
    fun inject(activity: SplashActivity)
    fun inject(activity: MainActivity)
    fun inject(fragment: RepositoriesFragment)
}