package com.fifed.ui.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.fifed.ui.extension.toPx

private val EMPTY = 0.dp

@Composable
fun RemoteImage(url: String, modifier: Modifier = Modifier, maxSize: Dp = EMPTY, loading: @Composable (() -> Unit)? = null) {
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }
    Glide
        .with(LocalContext.current)
        .asBitmap()
        .load(url)
        .run {
            if (maxSize != EMPTY) {
                override(maxSize.toPx())
            } else {
                this
            }
        }
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                bitmapState.value = placeholder?.toBitmap()
            }
        })
    val bitmap = bitmapState.value
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (bitmap != null) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null
            )
        } else if (loading != null) {
            loading()
        }
    }
}