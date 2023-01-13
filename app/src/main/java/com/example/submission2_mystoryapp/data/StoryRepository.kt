package com.example.submission2_mystoryapp.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.example.submission2_mystoryapp.data.remote.ApiService
import com.example.submission2_mystoryapp.data.remote.response.ListStory
import com.example.submission2_mystoryapp.data.remote.response.LoginResult
import com.example.submission2_mystoryapp.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.example.submission2_mystoryapp.data.lokal.room.StoryDatabase

class StoryRepository (
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase,
) {

    fun login(email: String, password: String): LiveData<Result<LoginResult?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response.loginResult))
        } catch (e: Exception){
            emit(Result.Error(e.message.toString()))
            Log.d(TAG, "onFailure: ${e.message.toString()}")
        }
    }

    fun signup (name: String, email: String, password: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        val registerResponse = MutableLiveData<StoryResponse>()
        try {
            val response = apiService.signup(name, email, password)
            registerResponse.postValue(response)
            emit(Result.Success(response.message))
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addNewStory(token: String, image: MultipartBody.Part, description: RequestBody): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addNewStory(token, image, description)
            emit(Result.Success(response.message))
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllStory(token: String) : LiveData<PagingData<ListStory>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getMaps(token: String): LiveData<Result<StoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getMaps(token, 1)
            emit(Result.Success(response))
        } catch (e: Exception){
            Log.d(TAG, "onFailure: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(
            apiService: ApiService,
            storyDatabase: StoryDatabase
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, storyDatabase)
            }.also { instance = it }
    }
}