package com.ckc.latestnews.hilt_module

import com.ckc.latestnews.RetrofitInstance
import com.ckc.latestnews.repository.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAPIService(): APIService {
        return RetrofitInstance.api
    }
}