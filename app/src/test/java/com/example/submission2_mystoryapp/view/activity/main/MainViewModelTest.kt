package com.example.submission2_mystoryapp.view.activity.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.submission2_mystoryapp.adapter.StoryAdapter
import com.example.submission2_mystoryapp.data.StoryRepository
import com.example.submission2_mystoryapp.data.remote.response.ListStory
import com.example.submission2_mystoryapp.utils.DataDummy
import com.example.submission2_mystoryapp.utils.MainDispatcherRule
import com.example.submission2_mystoryapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : ViewModel(){

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mainViewModel: MainViewModel
    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(storyRepository)
    }

    @Test
    fun `when getAllStory Should Not Null`() = runTest {
        val listStory = DataDummy.listStoryItem()
        val data: PagingData<ListStory> = StoryPagingSource.snapshot(listStory)
        val expectedStory = MutableLiveData<PagingData<ListStory>>()
        expectedStory.value = data
        Mockito.`when`(storyRepository.getAllStory(dummyToken)).thenReturn(expectedStory)
        val actualStory = mainViewModel.getStory(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        advanceUntilIdle()
        Mockito.verify(storyRepository).getAllStory(dummyToken)
        assertNotNull(differ.snapshot())
        assertEquals(listStory.size, differ.snapshot().size)
        assertEquals(listStory[0].id, differ.snapshot()[0]?.id)
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<ListStory>>>() {
    companion object {
        fun snapshot(items: List<ListStory>): PagingData<ListStory> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStory>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStory>>> {
        return LoadResult.Page(emptyList(), 0 , 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}