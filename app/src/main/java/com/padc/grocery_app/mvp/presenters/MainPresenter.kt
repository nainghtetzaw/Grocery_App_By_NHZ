package com.padc.grocery_app.mvp.presenters

import android.graphics.Bitmap
import com.padc.grocery.mvp.presenters.BasePresenter
import com.padc.grocery_app.delegates.GroceryDelegate
import com.padc.grocery_app.mvp.views.MainView

interface MainPresenter : BasePresenter<MainView>, GroceryDelegate {
    fun onTapAddGrocery(name : String,description : String,amount : Int,image : String)
    fun onPhotoTaken(bitmap : Bitmap,onSuccess : (imageUrl : String) -> Unit )
}