package com.example.submission2_mystoryapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class SignUpResponse (

    @field:SerializedName("error")
    val error: Boolean,

) : Parcelable