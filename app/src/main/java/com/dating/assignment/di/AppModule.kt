package com.dating.assignment.di

import com.dating.assignment.data.remote.AssignmentApi
import com.dating.assignment.util.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    // Function to provide the Retrofit instance
    fun provideRetrofitInstance(): AssignmentApi {
        // Return a new Retrofit instance with the specified configuration
        return Retrofit.Builder()
            // Set the base URL for the API
            .baseUrl(BASE_URL)
            // Add Gson converter factory for handling JSON serialization and deserialization
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            // Create an instance of the AssignmentApi interface
            .create(AssignmentApi::class.java)
    }
}
