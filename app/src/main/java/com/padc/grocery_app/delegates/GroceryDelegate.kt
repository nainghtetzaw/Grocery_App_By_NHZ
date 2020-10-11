package com.padc.grocery_app.delegates

import com.padc.grocery.data.vos.GroceryVO

interface GroceryDelegate{
    fun onTapDeleteGrocery(name : String)
    fun onTapEditGrocery(name: String, description: String, amount: Int)
    fun onTapUpload(grocery : GroceryVO)
}