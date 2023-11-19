package com.dating.assignment.repository

import com.dating.assignment.model.NewsResponse
import com.dating.assignment.data.remote.AssignmentApi
import javax.inject.Inject
import retrofit2.Response

// Implementation of the Repository interface
class RepositoryImpl @Inject constructor(
    // Inject the AssignmentApi dependency
    private val api: AssignmentApi
) : Repository {

    // Implementation of the getData method from the Repository interface
    override suspend fun getData(countryCode: String, pageNumber: Int): Response<NewsResponse> =
        // Call the getBreakingNews method from the injected AssignmentApi instance
        api.getBreakingNews(countryCode, pageNumber)
}
