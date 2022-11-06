package com.example.movieapp.extentions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movieapp.R

fun ImageView.setImage(url: String?) {
    val image = "https://image.tmdb.org/t/p/w500$url"
    Glide.with(context)
        .load(image)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone(isAnimated: Boolean = false) {
    if (isAnimated) clearAnimation()
    visibility = View.GONE
}