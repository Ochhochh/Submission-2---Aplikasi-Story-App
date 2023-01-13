package com.example.submission2_mystoryapp.di

import android.content.Context
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.data.lokal.room.StoryDatabase
import com.example.submission2_mystoryapp.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getInstance(context)
        return StoryRepository.getInstance(apiService, database)
    }
}