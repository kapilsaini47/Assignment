package com.dating.assignment.data.remote

import com.dating.assignment.model.NewsResponse
import com.dating.assignment.util.Utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AssignmentApi {

    // Defined a suspend function to get breaking news
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        // Query parameter for the country code
        @Query("country") countryCode: String,

        // Query parameter for the page number
        @Query("page") pageNumber: Int,

        // Query parameter for the API key
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}