package com.padc.grocery_app.mvp.presenters.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery.mvp.presenters.AbstractBasePresenter
import com.padc.grocery_app.data.models.GroceryModel
import com.padc.grocery_app.data.models.GroceryModelImpl
import com.padc.grocery_app.mvp.presenters.MainPresenter
import com.padc.grocery_app.mvp.views.MainView

class MainPresenterImpl : MainPresenter, AbstractBasePresenter<MainView>() {

    private val mModel : GroceryModel = GroceryModelImpl
//    private var mUpLoadGrocery : GroceryVO? = null

    override fun onTapAddGrocery(name: String, description: String, amount: Int,image : String) {
        mModel.addOrUpdateGrocery(name,description,amount,image)
    }

    override fun onPhotoTaken(bitmap: Bitmap,onSuccess : (imageUrl : String) -> Unit) {
//        mUpLoadGrocery?.let {
            mModel.uploadGrocery(bitmap,onSuccess)
//        }
    }

    override fun onUiReady(owner: LifecycleOwner) {
        mModel.getGrocery(onSuccess = {
            mView.showGroceryView(it)
        },onFailure = {})
    }

    override fun onTapDeleteGrocery(name: String) {
        mModel.deleteGrocery(name)
    }

    override fun onTapEditGrocery(name: String, description: String, amount: Int,image : String) {
        mView.showGroceryDialog(name,description,amount.toString(),image)
    }

}