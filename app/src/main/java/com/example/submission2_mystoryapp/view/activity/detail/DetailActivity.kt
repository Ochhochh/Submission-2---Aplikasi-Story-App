package com.example.submission2_mystoryapp.view.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.submission2_mystoryapp.data.remote.response.ListStory
import com.example.submission2_mystoryapp.databinding.ActivityDetailBinding

class DetailActivity :  AppCompatActivity() {
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listStoryItem: ListStory? = intent.getParcelableExtra(EXTRA_DETAIL)
        binding.apply{
            if (listStoryItem != null) {
                Glide.with(this@DetailActivity)
                    .load(listStoryItem.photoUrl)
                    .circleCrop()
                    .into(binding.imgItemPhoto)
                tvName.text = listStoryItem.name
                tvDescription.text = listStoryItem.description
            }
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}