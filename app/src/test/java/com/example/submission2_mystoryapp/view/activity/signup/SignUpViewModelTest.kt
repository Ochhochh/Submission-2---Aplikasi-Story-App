package com.example.submission2_mystoryapp.view.activity.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.utils.DataDummy
import com.example.submission2_mystoryapp.utils.getOrAwaitValue
import com.example.submission2_mystoryapp.data.Result
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: SignUpViewModel
    private val dummyName = DataDummy.generateDummyName()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()

    @Before
    fun setUp() {
        registerViewModel = SignUpViewModel(storyRepository)
    }

    @Test
    fun `when register Should Not Null and Return Success`() {
        val expectedRegister = MutableLiveData<Result<String>>()
        expectedRegister.value = Result.Success("User Created")
        Mockito.`when`(storyRepository.signup(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedRegister)
        val actualRegister = registerViewModel.signup(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).signup(dummyName, dummyEmail, dummyPassword)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Success)
    }

    @Test
    fun `when register Error Should Return Error`() {
        val expectedRegister = MutableLiveData<Result<String>>()
        expectedRegister.value = Result.Error("Error")
        Mockito.`when`(storyRepository.signup(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedRegister)
        val actualRegister = registerViewModel.signup(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).signup(dummyName, dummyEmail, dummyPassword)
        assertNotNull(actualRegister)
        assertTrue(actualRegister is Result.Error)
    }
}