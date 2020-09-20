package com.padc.grocery_app.data.models

import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.network.FirebaseApi
import com.padc.grocery_app.network.FirebaseApiImpl

object GroceryModelImpl : GroceryModel {

    override var mFireBaseApi: FirebaseApi = FirebaseApiImpl

    override fun getGrocery(onSuccess: (List<GroceryVO>) -> Unit, onFailure: (String) -> Unit) {
        mFireBaseApi.getGrocery(onSuccess,onFailure)
    }

    override fun addOrUpdateGrocery(name: String, description: String, amount: Int) {
        mFireBaseApi.addAndUpdateGrocery(name,description, amount)
    }

    override fun deleteGrocery(name: String) {
        mFireBaseApi.deleteGrocery(name)
    }
}