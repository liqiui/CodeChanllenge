package com.example.codechallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.data.Show
import com.example.codechallenge.databinding.ShowItemBinding

class DataAdapter: ListAdapter<Show, DataAdapter.ShowItemHolder>(
    DiffCallback
) {
    class ShowItemHolder(private var binding: ShowItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(show: Show) {
            binding.show = show
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowItemHolder {
        return ShowItemHolder( ShowItemBinding.inflate( LayoutInflater.from( parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ShowItemHolder, position: Int) {
        val show = getItem(position)
        holder.bind(show)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.image.showImage === newItem.image.showImage
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }

    }
}