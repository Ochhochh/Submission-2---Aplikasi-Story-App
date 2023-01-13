package com.example.submission2_mystoryapp.view.activity.login

import androidx.lifecycle.ViewModel
import com.example.submission2_mystoryapp.data.StoryRepository

class LoginViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) = storyRepository.login(email, password)
}