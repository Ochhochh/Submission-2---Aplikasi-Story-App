package com.example.submission2_mystoryapp.view.activity.maps

import androidx.lifecycle.ViewModel
import com.example.submission2_mystoryapp.data.StoryRepository

class MapsViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun getMaps(token: String) =  storyRepository.getMaps(token)
}