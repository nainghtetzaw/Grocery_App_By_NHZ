package com.padc.grocery_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.R
import com.padc.grocery_app.delegates.GroceryDelegate
import com.padc.grocery_app.viewholders.GroceryViewholder

class GroceryAdapter(delegate : GroceryDelegate) : BaseAdapter<GroceryViewholder,GroceryVO>() {

    private val mDelegate : GroceryDelegate = delegate

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grocery,parent,false)
        return GroceryViewholder(view,mDelegate)
    }
}