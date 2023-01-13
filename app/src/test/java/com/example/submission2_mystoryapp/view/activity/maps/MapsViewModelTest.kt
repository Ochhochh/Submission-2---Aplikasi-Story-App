package com.example.submission2_mystoryapp.view.activity.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.data.remote.response.StoryResponse
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
class MapsViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyStory = DataDummy.generateDummyStory()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when getAllStory with location Should Not Null and Return Success`() {
        val expectedMaps = MutableLiveData<Result<StoryResponse>>()
        expectedMaps.value = Result.Success(dummyStory)
        `when`(storyRepository.getMaps(dummyToken)).thenReturn(expectedMaps)
        val actualMaps = mapsViewModel.getMaps(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getMaps(dummyToken)
        assertNotNull(actualMaps)
        assertTrue(actualMaps is Result.Success)
        assertEquals(dummyStory, (actualMaps as Result.Success).data)
    }
}