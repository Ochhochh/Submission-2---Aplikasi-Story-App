package com.example.submission2_mystoryapp.view.activity.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.di.Injection

class SignUpViewModelFactory private constructor(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
                return SignUpViewModel(storyRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

        companion object {
            private var instance: SignUpViewModelFactory? = null

            fun getInstance(context: Context): SignUpViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: SignUpViewModelFactory(Injection.provideRepository(context))
                }.also { instance = it }
        }
    }