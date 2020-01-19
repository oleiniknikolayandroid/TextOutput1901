package com.example.textoutput_190120.di.component

import com.example.textoutput_190120.di.module.AppModule
import com.example.textoutput_190120.di.scope.AppScope
import com.google.gson.Gson

import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    val gson: Gson
}