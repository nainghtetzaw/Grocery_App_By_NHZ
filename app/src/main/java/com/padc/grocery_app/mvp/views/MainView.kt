package com.padc.grocery_app.mvp.views

import com.padc.grocery.data.vos.GroceryVO

interface MainView : BaseView {
    fun showGroceryView(groceryList : List<GroceryVO>)
    fun showGroceryDialog(name : String,description : String,amount : String)
}