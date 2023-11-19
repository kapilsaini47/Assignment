package com.dating.assignment.repository

import com.dating.assignment.model.NewsResponse
import retrofit2.Response

// Repository interface to define data retrieval operations
interface Repository {

    // Suspend function for fetching data asynchronously
    suspend fun getData(countryCode: String, pageNumber: Int): Response<NewsResponse>
}