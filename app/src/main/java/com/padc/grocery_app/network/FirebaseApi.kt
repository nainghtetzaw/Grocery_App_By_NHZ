package com.padc.grocery_app.network

import com.padc.grocery.data.vos.GroceryVO

interface FirebaseApi {
    fun getGrocery(onSuccess : (groceries : List<GroceryVO>) -> Unit, onFailure : (String) -> Unit)
    fun addAndUpdateGrocery(name : String,description : String,amount : Int)
    fun deleteGrocery(name: String)
}