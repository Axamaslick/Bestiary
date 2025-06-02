package com.example.bestiary.presentation.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

interface ImageLoader {
    fun loadImage(url: String?, imageView: ImageView)
    fun loadImageWithPlaceholder(url: String?, imageView: ImageView, placeholder: Int)
}

class GlideImageLoader @Inject constructor(
    private val context: Context
) : ImageLoader {
    override fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    override fun loadImageWithPlaceholder(url: String?, imageView: ImageView, placeholder: Int) {
        Glide.with(context)
            .load(url)
            .placeholder(placeholder)
            .into(imageView)
    }
}