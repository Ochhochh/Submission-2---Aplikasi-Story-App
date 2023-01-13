package com.example.submission2_mystoryapp.utils

import com.example.submission2_mystoryapp.data.remote.response.ListStory
import com.example.submission2_mystoryapp.data.remote.response.LoginResult
import com.example.submission2_mystoryapp.data.remote.response.StoryResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyLoginResult(): LoginResult{
        return LoginResult(
            "123456",
            "ochataniya",
            "123456",
            true,
        )
    }

    fun generateDummyName(): String{
        return "ochataniya"
    }

    fun generateDummyEmail(): String{
        return "taniya@gmail.com"
    }

    fun generateDummyPassword(): String{
        return "chachamarica"
    }

    fun generateDummyToken(): String{
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTk2NVdzMkJJSkxKc2RtV28iLCJpYXQiOjE2NzEzNjgwNTl9.CIcJYmwctLHoJMcem1GBFjgbByNiNLfk4DmqCKnhGNM"
    }

    fun listStoryItem(): List<ListStory>{
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val list = ListStory(
                "$i",
                "name: $i",
                "description: $i",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                -6.178043,
                106.684249
            )
            items.add(list)
        }
        return items
    }

    fun generateDummyStory(): StoryResponse{
        return StoryResponse(false,"Success", null, listStoryItem())
    }

    fun generateDummyImages(): MultipartBody.Part {
        return MultipartBody.Part.createFormData("twitter", "https://story-api.dicoding.dev/images/stories/photos-1665640858424_MIPUfQux.png")
    }

    fun generateDummyDescription(): RequestBody {
        return "desc".toRequestBody("text/plain".toMediaType())
    }

    fun generateDummyRegister(): StoryResponse {
        return StoryResponse(false,"success", null, null)
    }

    fun generateDummyAddNewStory(): StoryResponse {
        return StoryResponse(false, "success", null, null)
    }

}