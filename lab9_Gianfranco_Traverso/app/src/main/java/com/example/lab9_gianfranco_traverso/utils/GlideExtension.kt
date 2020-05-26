package com.example.lab9_gianfranco_traverso.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideAppModule: AppGlideModule()

fun ImageView.loadGif(uri: String?) {
    Glide.with(this.context)
        .asGif()
        .centerInside()
        .load(uri)
        .into(this)
}