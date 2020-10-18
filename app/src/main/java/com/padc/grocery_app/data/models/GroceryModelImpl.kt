package com.padc.grocery_app.data.models

import android.graphics.Bitmap
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.network.FIrestoreApiImpl
import com.padc.grocery_app.network.FirebaseApi
import com.padc.grocery_app.network.RealtimeDatabaseImpl

object GroceryModelImpl : GroceryModel {

    override var mFireBaseApi: FirebaseApi = FIrestoreApiImpl

    override fun getGrocery(onSuccess: (List<GroceryVO>) -> Unit, onFailure: (String) -> Unit) {
        mFireBaseApi.getGrocery(onSuccess,onFailure)
    }

    override fun addOrUpdateGrocery(name: String, description: String, amount: Int,image : String) {
        mFireBaseApi.addAndUpdateGrocery(name,description, amount,image)
    }

    override fun deleteGrocery(name: String) {
        mFireBaseApi.deleteGrocery(name)
    }

    override fun uploadGrocery(image: Bitmap,onSuccess : (imageUrl : String) -> Unit) {
        mFireBaseApi.uploadGrocery(image,onSuccess)
    }
}