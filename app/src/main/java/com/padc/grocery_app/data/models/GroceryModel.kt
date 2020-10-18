package com.padc.grocery_app.data.models

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.network.FirebaseApi

interface GroceryModel {

    var mFireBaseApi : FirebaseApi

    fun getGrocery(onSuccess : (List<GroceryVO>) -> Unit, onFailure : (String) -> Unit)
    fun addOrUpdateGrocery(name : String,description : String,amount : Int,image : String)
    fun deleteGrocery(name : String)
    fun uploadGrocery(image : Bitmap,onSuccess: (imageUrl : String) -> Unit)
}