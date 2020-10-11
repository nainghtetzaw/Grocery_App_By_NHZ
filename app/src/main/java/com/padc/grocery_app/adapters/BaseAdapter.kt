package com.padc.grocery_app.adapters

import androidx.recyclerview.widget.RecyclerView
import com.padc.grocery_app.viewholders.BaseViewHolder

abstract class BaseAdapter<T : BaseViewHolder<W>,W> : RecyclerView.Adapter<T>() {

    var mData : MutableList<W> = mutableListOf()

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bindData(mData[position])
    }

    fun setNewData(data : List<W>){
        mData = data.toMutableList()
        notifyDataSetChanged()
    }

}