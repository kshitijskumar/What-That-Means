package com.example.whatthatmeans.di

import com.example.whatthatmeans.retrofit.DictionaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DictionaryModule {

    @Singleton
    @Provides
    fun providesDictionaryRetrofit(): Retrofit= Retrofit.Builder()
        .baseUrl("https://api.dictionaryapi.dev")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesDictionaryInstance(retrofit: Retrofit): DictionaryService= retrofit
        .create(DictionaryService::class.java)
}