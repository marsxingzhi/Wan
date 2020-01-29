package com.abyte.wan.ext

import cn.carbs.android.avatarimageview.library.AvatarImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView


fun AvatarImageView.loadWithGlide(
    url: String,
    textPlaceHolder: Char,
    requestOptions: RequestOptions = RequestOptions()
) {
    textPlaceHolder.toString().let {
        setTextAndColorSeed(it.toUpperCase(), it.hashCode().toString())
    }
    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}

fun RoundedImageView.loadWithGlide(
    url: String,
    requestOptions: RequestOptions = RequestOptions()
) {
    Glide.with(this.context)
        .load(url)
        .apply(requestOptions)
        .into(this)
}