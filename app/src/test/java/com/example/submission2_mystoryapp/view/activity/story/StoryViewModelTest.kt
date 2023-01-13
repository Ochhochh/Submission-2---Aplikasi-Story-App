package com.example.submission2_mystoryapp.view.activity.story

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
class StoryViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addViewModel: StoryViewModel
    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyPhoto = DataDummy.generateDummyImages()
    private val dummyDesc = DataDummy.generateDummyDescription()

    @Before
    fun setUp() {
        addViewModel = StoryViewModel(storyRepository)
    }

    @Test
    fun `when addNewStory Should Not Null and Return Success`() {
        val expectedAddStory = MutableLiveData<Result<String>>()
        expectedAddStory.value = Result.Success("Upload Success")
        Mockito.`when`(storyRepository.addNewStory(dummyToken, dummyPhoto, dummyDesc)).thenReturn(expectedAddStory)
        val actualAddStory = addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc).getOrAwaitValue()

        Mockito.verify(storyRepository).addNewStory(dummyToken, dummyPhoto, dummyDesc)
        assertNotNull(actualAddStory)
        assertTrue(actualAddStory is Result.Success)
    }

    @Test
    fun `when addNewStory Error and Return Error`() {
        val expectedAddStory = MutableLiveData<Result<String>>()
        expectedAddStory.value = Result.Error("Error")
        Mockito.`when`(storyRepository.addNewStory(dummyToken, dummyPhoto, dummyDesc)).thenReturn(expectedAddStory)
        val actualAddStory = addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc).getOrAwaitValue()

        Mockito.verify(storyRepository).addNewStory(dummyToken, dummyPhoto, dummyDesc)
        assertNotNull(actualAddStory)
        assertTrue(actualAddStory is Result.Error)
    }
}