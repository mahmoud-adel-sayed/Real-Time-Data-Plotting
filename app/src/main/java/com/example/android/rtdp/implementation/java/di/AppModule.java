package com.example.android.rtdp.implementation.java.di;

import com.example.android.rtdp.implementation.java.Config;
import com.example.android.rtdp.implementation.java.data.service.MainService;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class AppModule {
    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        httpClient.addInterceptor(logging);
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Singleton
    @Provides
    MainService provideMainService(Retrofit retrofit) {
        return retrofit.create(MainService.class);
    }
}