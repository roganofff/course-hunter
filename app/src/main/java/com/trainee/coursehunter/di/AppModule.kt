package com.trainee.coursehunter.di

import com.trainee.data.api.RetrofitInstance
import com.trainee.data.repository.CoursesRepositoryImpl
import com.trainee.domain.repository.CoursesRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCoursesRepository() : CoursesRepositoryInterface =
        CoursesRepositoryImpl(RetrofitInstance.api)
}