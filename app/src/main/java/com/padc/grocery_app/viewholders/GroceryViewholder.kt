package com.padc.grocery_app.viewholders

import android.view.View
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.delegates.GroceryDelegate
import kotlinx.android.synthetic.main.item_grocery.view.*

class GroceryViewholder(itemview : View,val mDelegate: GroceryDelegate) : BaseViewHolder<GroceryVO>(itemview) {
    override fun bindData(data: GroceryVO) {
        itemView.tvGroceryName.text = data.name
        itemView.tvGroceryDescription.text = data.description
        itemView.tvNumberOfGrocery.text = "+${data.amount}"

        itemView.imgEdit.setOnClickListener {
            mDelegate.onTapEditGrocery(data.name ?: "",data.description ?: "",data.amount ?: 0)
        }
        itemView.imgDelete.setOnClickListener {
            mDelegate.onTapDeleteGrocery(data.name ?: "")
        }
    }
}