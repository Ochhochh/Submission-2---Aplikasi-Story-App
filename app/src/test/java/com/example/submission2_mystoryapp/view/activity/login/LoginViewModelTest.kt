package com.example.submission2_mystoryapp.view.activity.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.data.remote.response.LoginResult
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.generateDummyLoginResult()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when login Should Not Null and Return Success`() {
        val expectedLogin = MutableLiveData<Result<LoginResult?>>()
        expectedLogin.value = Result.Success(dummyLogin)
        `when`(storyRepository.login(dummyEmail, dummyPassword)).thenReturn(expectedLogin)
        val actualLogin = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).login(dummyEmail, dummyPassword)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Success)
    }

    @Test
    fun `when login Error Should Return Error`() {
        val expectedLogin = MutableLiveData<Result<LoginResult?>>()
        expectedLogin.value = Result.Error("Error")
        `when`(storyRepository.login(dummyEmail, dummyPassword)).thenReturn(expectedLogin)
        val actualLogin = loginViewModel.login(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).login(dummyEmail, dummyPassword)
        assertNotNull(actualLogin)
        assertTrue(actualLogin is Result.Error)
    }
}