package com.adventitous.matall.core.genericadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T>(
    private var items: List<T>,
    private val layoutId: Int,
    private val bind: (View, T) -> Unit,
    private val itemClickListener: ((T) -> Unit)? = null
) : RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    class GenericViewHolder<T>(
        view: View,
        private val bind: (View, T) -> Unit,
        private val itemClickListener: ((T) -> Unit)?
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: T) {
            bind(itemView, item)
            itemView.setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return GenericViewHolder(view, bind, itemClickListener)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newItems: List<T>) {
        items = newItems
        notifyDataSetChanged()
    }
}
