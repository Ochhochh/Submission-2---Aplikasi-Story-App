package com.example.submission2_mystoryapp.view.activity.signup

import androidx.lifecycle.ViewModel
import com.example.submission2_mystoryapp.data.StoryRepository

class SignUpViewModel (private val storyRepository: StoryRepository) : ViewModel() {
    fun signup(name: String, email: String, password: String) = storyRepository.signup(name, email, password)
}