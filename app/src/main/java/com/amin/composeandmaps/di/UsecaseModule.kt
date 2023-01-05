package com.amin.composeandmaps.di

import com.amin.composeandmaps.data.car.CarRepository
import com.amin.composeandmaps.data.usecases.GetItemsForLocationUseCase
import com.amin.composeandmaps.data.usecases.impl.GetItemsForLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    @Singleton
    fun provideGetItemsUsecase(carRepository: CarRepository): GetItemsForLocationUseCase {
        return GetItemsForLocationUseCaseImpl(carRepository)
    }
}
