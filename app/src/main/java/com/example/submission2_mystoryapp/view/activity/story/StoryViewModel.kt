package com.example.submission2_mystoryapp.view.activity.story

import androidx.lifecycle.ViewModel
import com.example.submission2_mystoryapp.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun addNewStory(token: String, image: MultipartBody.Part, description: RequestBody)
            = storyRepository.addNewStory(token, image, description)
}