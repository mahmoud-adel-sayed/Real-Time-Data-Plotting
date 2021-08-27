package com.example.android.rtdp.implementation.kotlin.di

import android.app.Application
import com.example.android.rtdp.implementation.kotlin.data.LiveDataCallAdapterFactory
import com.example.android.rtdp.implementation.kotlin.data.service.MainService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://my-json-server.typicode.com/mahmoud-adel-sayed/Real-Time-Data-Plotting/"

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(app: Application): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(StethoInterceptor())
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory(app))
                .client(httpClient.build())
                .build()
    }

    @Singleton
    @Provides
    fun provideMainService(retrofit: Retrofit): MainService = retrofit.create(MainService::class.java)
}