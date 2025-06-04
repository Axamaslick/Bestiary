package com.example.bestiary.presentation.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.bestiary.R
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
            .error(R.drawable.ic_error) // Добавить placeholder для ошибок
            .into(imageView)
    }

    override fun loadImageWithPlaceholder(url: String?, imageView: ImageView, placeholder: Int) {
        Glide.with(context)
            .load(url ?: R.drawable.ic_empty_placeholder) // Обработка null
            .placeholder(placeholder)
            .error(R.drawable.ic_error)
            .into(imageView)
    }
}