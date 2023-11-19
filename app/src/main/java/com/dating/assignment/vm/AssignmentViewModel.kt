package com.dating.assignment.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dating.assignment.model.NewsResponse
import com.dating.assignment.repository.Repository
import com.dating.assignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AssignmentViewModel @Inject constructor(
    // Injected dependencies: Repository and Application
    private val repository: Repository,
    private val app: Application
) : ViewModel() {

    // LiveData to hold the response data with Resource wrapper
    private val _dummyDataResponse: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    // Public LiveData for observing the response
    val dummyDataResponse: LiveData<Resource<NewsResponse>> get() = _dummyDataResponse

    // Initialization block to trigger the data retrieval when the ViewModel is created
    init {
        getData()
    }

    // Function to initiate data retrieval
    private fun getData() = viewModelScope.launch {
        // Call the getArticles function with default parameters
        getArticles("in", 1)
    }

    // Coroutine function for fetching articles
    private suspend fun getArticles(countryCode: String, pageNumber: Int) {
        // Set the LiveData to Loading state
        _dummyDataResponse.value = Resource.Loading()

        try {
            // Call the repository to get data
            val response = repository.getData(countryCode, pageNumber)

            // Check if the response is successful
            if (response.isSuccessful) {
                // If successful, set the LiveData to Success state with the retrieved data
                response.body()?.let {
                    _dummyDataResponse.value = Resource.Success(it)
                }
            } else {
                // If not successful, set the LiveData to Error state with the error message
                _dummyDataResponse.value = Resource.Error(response.message().toString(), null)
            }
        } catch (t: Throwable) {
            // Handle exceptions during the data retrieval process
            when (t) {
                is IOException -> _dummyDataResponse.value = Resource.Error("Network failure")
                else -> _dummyDataResponse.value = Resource.Error(t.message.toString())
            }
        }
    }
}
