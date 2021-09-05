package com.example.codechallenge.ui.show

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.codechallenge.DataAdapter
import com.example.codechallenge.R
import com.example.codechallenge.data.Show

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     payload: List<Show>?) {
    val adapter = recyclerView.adapter as DataAdapter
    adapter.submitList(payload)
}

@BindingAdapter("imageUrl")
fun bindImageView(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imageView)
    }
}