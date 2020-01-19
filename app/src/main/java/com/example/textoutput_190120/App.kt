package com.example.textoutput_190120

import androidx.room.Room
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.textoutput_190120.di.component.*
import com.example.textoutput_190120.di.module.*
import com.example.textoutput_190120.usecases.repository.data_source.database.AppDatabase
import com.example.textoutput_190120.utils.DATABASE_NAME

class App: MultiDexApplication() {

    private lateinit var viewModelComponent: ViewModelComponent
    private lateinit var database: AppDatabase

    init {
        applicationInstance = this
    }

    companion object {
        private lateinit var applicationInstance: App
//provides an Application
        fun get(): App {
            return applicationInstance.applicationContext as App
        }
    }
//eanble  MultiDex
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
//init methods at onCreate()
    override fun onCreate() {
        super.onCreate()
        initRoom()
        initDagger()
    }
//init database
    private fun initRoom() {
        database = Room.databaseBuilder(this, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }
//init dagger
    private fun initDagger() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        val apiComponent = DaggerApiComponent.builder()
            .appComponent(appComponent)
            .apiModule(ApiModule())
            .build()

        val databaseComponent = DaggerDatabaseComponent.builder()
            .databaseModule(DatabaseModule(database))
            .build()

        val repositoryComponent = DaggerRepositoryComponent.builder()
            .apiComponent(apiComponent)
            .databaseComponent(databaseComponent)
            .repositoryModule(RepositoryModule())
            .build()

        val interactorComponent = DaggerInteractorComponent.builder()
            .repositoryComponent(repositoryComponent)
            .interactorModule(InteractorModule())
            .build()

        viewModelComponent = DaggerViewModelComponent.builder()
            .interactorComponent(interactorComponent)
            .viewModelModule(ViewModelModule(this))
            .build()
    }

    fun getViewModelComponent(): ViewModelComponent {
        return this.viewModelComponent
    }
}