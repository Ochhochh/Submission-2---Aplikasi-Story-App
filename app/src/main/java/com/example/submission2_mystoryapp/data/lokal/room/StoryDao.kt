package com.example.submission2_mystoryapp.data.lokal.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission2_mystoryapp.data.remote.response.ListStory

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(ListStory: List<ListStory>)

    @Query("SELECT * FROM ListStory")
    fun getAllStory(): PagingSource<Int, ListStory>

    @Query("DELETE FROM ListStory")
    suspend fun deleteAll()
}