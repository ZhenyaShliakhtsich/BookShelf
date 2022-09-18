package com.example.bookshelf.di

import com.example.bookshelf.ui.*
import dagger.Component
import javax.inject.Singleton


@Component(modules = [AppModule::class, NetworkModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(fragment : BookFragment)

    fun inject(fragment : CartFragment)

    fun inject(fragment : FavouriteFragment)

    fun inject(fragment : SearchFragment)

    fun inject(fragment : MainFragment)

    fun inject(fragment : HomeFragment)
}