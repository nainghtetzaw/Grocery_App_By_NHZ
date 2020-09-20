package com.padc.grocery_app.delegates

interface GroceryDelegate{
    fun onTapDeleteGrocery(name : String)
    fun onTapEditGrocery(name: String, description: String, amount: Int)
}