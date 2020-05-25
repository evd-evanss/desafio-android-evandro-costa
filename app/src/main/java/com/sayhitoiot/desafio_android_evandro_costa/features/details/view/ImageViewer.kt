package com.sayhitoiot.desafio_android_evandro_costa.features.details.view

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sayhitoiot.desafio_android_evandro_costa.R
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar


class ImageViewer {

    fun setImageWithUrl(url: String?, view: ImageView?, progress: DilatingDotsProgressBar?) {
        Picasso
            .get()
            .load(url)
            .centerCrop()
            .fit()
            .error(R.drawable.ic_error)
            .into(view, object : Callback{
                override fun onSuccess() {
                    progress?.hide()
                }

                override fun onError(e: Exception?) {
                    progress?.hide()
                }

            })
    }

    fun setImageWithUrlAndNotCache(url: String?, view: ImageView?, progress: DilatingDotsProgressBar?) {
        Picasso
            .get()
            .load(url)
            .centerCrop()
            .fit()
            .error(R.drawable.ic_error)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(view, object : Callback{
                override fun onSuccess() {
                    progress?.hide()
                }

                override fun onError(e: Exception?) {
                    progress?.hide()
                }

            })
    }

    fun setImageWithResource(resource: Int, view: ImageView, progress: DilatingDotsProgressBar?) {
        Picasso
            .get()
            .load(resource)
            .centerCrop()
            .fit()
            .error(R.drawable.ic_error)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .into(view, object : Callback{
                override fun onSuccess() {
                    progress?.hide()
                }

                override fun onError(e: Exception?) {
                    progress?.hide()
                }

            })
    }

    fun setRoundImage(context: Context, resource: Int, view: ImageView?) {
        view?.let {

            Glide.with(context)
                .load(resource)
                .apply(
                    RequestOptions()
                        .circleCrop()
                ).into(it)

        }
    }

}