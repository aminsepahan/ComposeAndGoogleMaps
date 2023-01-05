package com.amin.composeandmaps.di

import com.amin.composeandmaps.shared.manager.location.AminLocationManager
import com.amin.composeandmaps.shared.manager.location.AminLocationManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAminLocationManager(): AminLocationManager {
        return AminLocationManagerImpl()
    }
}
