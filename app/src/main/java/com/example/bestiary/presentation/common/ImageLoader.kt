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
        val fullUrl = when {
            url.isNullOrEmpty() -> null
            url.startsWith("/") -> "https://www.dnd5eapi.co$url"
            else -> url
        }

        Glide.with(context)
            .load(fullUrl)
            .placeholder(R.drawable.ic_loading_placeholder)
            .error(R.drawable.ic_error)
            .into(imageView)
    }

    override fun loadImageWithPlaceholder(url: String?, imageView: ImageView, placeholder: Int) {
        val fullUrl = when {
            url.isNullOrEmpty() -> null
            url.startsWith("/") -> "https://www.dnd5eapi.co$url"
            else -> url
        }

        Glide.with(context)
            .load(fullUrl)
            .placeholder(placeholder)
            .error(R.drawable.ic_error)
            .into(imageView)
    }
}