package com.example.codechallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.data.Show
import com.example.codechallenge.databinding.ShowItemBinding

class DataAdapter(private val onClickListener: OnClickListener): ListAdapter<Show, DataAdapter.ShowItemHolder>(
    DiffCallback
) {
    class OnClickListener(val clickListener: (show: Show) -> Unit) {
        fun onClick( show: Show) = clickListener( show)
    }

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
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user)
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