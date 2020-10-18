package com.padc.grocery_app.network

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO

interface FirebaseApi {
    fun getGrocery(onSuccess : (groceries : List<GroceryVO>) -> Unit, onFailure : (String) -> Unit)
    fun addAndUpdateGrocery(name : String,description : String,amount : Int,image : String)
    fun deleteGrocery(name: String)
    fun uploadGrocery(image : Bitmap,onSuccess : (imageUrl : String) -> Unit)
}