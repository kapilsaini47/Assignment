package com.dating.assignment.di

import com.dating.assignment.repository.Repository
import com.dating.assignment.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    // Abstract method to provide an instance of the Repository interface
    abstract fun repositoryImplInstance(
        // Parameter to be provided, in this case, an instance of RepositoryImpl
        repositoryImpl: RepositoryImpl
    ): Repository
}
