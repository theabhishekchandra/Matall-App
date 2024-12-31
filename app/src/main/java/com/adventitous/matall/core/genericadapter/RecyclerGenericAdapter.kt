package com.adventitous.matall.core.genericadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecyclerGenericAdapter<T>(
    context: Context,
    listener: PFRecyclerViewAdapter.OnViewHolderClick<T>,
    private val itemInterface: ItemInterface<T>
) : PFRecyclerViewAdapter<T>(context, listener) {

    private var layoutID: Int = 0


    fun setLayout(layoutID: Int) {
        this.layoutID = layoutID
    }

    override fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View {
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(layoutID, viewGroup, false)
    }

    override fun bindView(item: T?, viewHolder: PFRecyclerViewAdapter<T>.ViewHolder, position: Int) {
        item?.let {
            itemInterface.setItem(it, viewHolder, position)
        }
    }

    interface ItemInterface<T> {
        fun setItem(item: T, viewHolder: PFRecyclerViewAdapter<T>.ViewHolder, position: Int)
    }
}
