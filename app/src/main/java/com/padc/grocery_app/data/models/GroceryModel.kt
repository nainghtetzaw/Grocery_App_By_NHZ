package com.padc.grocery_app.data.models

import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.network.FirebaseApi
import com.padc.grocery_app.network.FirebaseApiImpl

interface GroceryModel {

    var mFireBaseApi : FirebaseApi

    fun getGrocery(onSuccess : (List<GroceryVO>) -> Unit, onFailure : (String) -> Unit)
    fun addOrUpdateGrocery(name : String,description : String,amount : Int)
    fun deleteGrocery(name : String)
}