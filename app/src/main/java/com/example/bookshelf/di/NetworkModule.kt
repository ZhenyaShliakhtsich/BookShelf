package com.example.bookshelf.di

import android.util.Log
import com.example.bookshelf.BASEURL
import com.example.bookshelf.network.ITBookStoreAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getITBookStoreApi(): ITBookStoreAPI {
        val okHTTPClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    Log.d("OK HTTP", message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
            .connectTimeout(15000L, TimeUnit.SECONDS)
            .readTimeout(15000L, TimeUnit.SECONDS)
            .writeTimeout(15000L, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHTTPClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ITBookStoreAPI::class.java)
    }
}