package com.example.bestiary.util

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.bestiary.R

@GlideModule
class AppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_loading_placeholder)
                .error(R.drawable.ic_error)
        )
    }

    override fun isManifestParsingEnabled(): Boolean = false
}