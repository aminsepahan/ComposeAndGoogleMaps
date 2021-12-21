package com.amin.composeandmaps.di

import com.amin.composeandmaps.data.car.CarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideCarService(retrofit: Retrofit): CarService {
        return retrofit.create(CarService::class.java)
    }

}
