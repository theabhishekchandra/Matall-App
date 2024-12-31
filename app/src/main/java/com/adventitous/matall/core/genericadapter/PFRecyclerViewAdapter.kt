package com.adventitous.matall.core.genericadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class PFRecyclerViewAdapter<T>(
    private val context: Context,
    private var listener: OnViewHolderClick<T>? = null
) : RecyclerView.Adapter<PFRecyclerViewAdapter<T>.ViewHolder>() {

    private var items: MutableList<T> = ArrayList()

    protected abstract fun createView(context: Context, viewGroup: ViewGroup, viewType: Int): View

    protected abstract fun bindView(item: T?, viewHolder: ViewHolder, position: Int)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = createView(context, viewGroup, viewType)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindView(getItem(position), holder, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(index: Int): T? {
        return if (index in 0 until items.size) items[index] else null
    }

    fun getList(): List<T> {
        return items
    }

    fun setList(list: List<T>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }

    fun setClickListener(listener: OnViewHolderClick<T>) {
        this.listener = listener
    }

    fun addAll(list: List<T>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun reset() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnViewHolderClick<T> {
        fun onClick(view: View, position: Int, item: T?)
    }

    inner class ViewHolder(view: View, private val listener: OnViewHolderClick<T>?) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        private val views: MutableMap<Int, View> = HashMap()

        init {
            views[0] = view
            listener?.let { view.setOnClickListener(this) }
        }

        override fun onClick(view: View) {
            listener?.onClick(view, adapterPosition, getItem(adapterPosition))
        }

        fun initViewList(idList: IntArray) {
            for (id in idList) {
                initViewById(id)
            }
        }

        fun initViewById(id: Int) {
            val foundView = itemView.findViewById<View>(id)
            foundView?.let { views[id] = it }
        }

        fun getView(): View? {
            return getView(0)
        }

        fun getView(id: Int): View? {
            if (!views.containsKey(id)) {
                initViewById(id)
            }
            return views[id]
        }
    }
}
