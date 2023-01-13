package com.example.submission2_mystoryapp.view.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.data.remote.response.ListStory

class MainViewModel (private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(token: String) : LiveData<PagingData<ListStory>>
            = storyRepository.getAllStory(token).cachedIn(viewModelScope)
}