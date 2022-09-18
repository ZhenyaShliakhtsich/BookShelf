package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.di.AppModule
import com.example.bookshelf.di.ApplicationComponent
import com.example.bookshelf.di.DaggerApplicationComponent
import com.example.bookshelf.di.NetworkModule


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }

    companion object {

        lateinit var appComponent: ApplicationComponent
    }

}
