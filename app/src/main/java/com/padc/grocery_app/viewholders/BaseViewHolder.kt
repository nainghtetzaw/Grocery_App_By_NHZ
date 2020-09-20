package com.padc.grocery_app.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemview : View) : RecyclerView.ViewHolder(itemview){

    val mData : T ?= null

    abstract fun bindData(data : T)

}