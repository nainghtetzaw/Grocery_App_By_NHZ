package com.padc.grocery_app.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.padc.grocery.mvp.presenters.AbstractBasePresenter
import com.padc.grocery_app.data.models.GroceryModel
import com.padc.grocery_app.data.models.GroceryModelImpl
import com.padc.grocery_app.mvp.presenters.MainPresenter
import com.padc.grocery_app.mvp.views.MainView

class MainPresenterImpl : MainPresenter, AbstractBasePresenter<MainView>() {

    private val mModel : GroceryModel = GroceryModelImpl

    override fun onTapAddGrocery(name: String, description: String, amount: Int) {
        mModel.addOrUpdateGrocery(name,description,amount)
    }

    override fun onUiReady(owner: LifecycleOwner) {
        mModel.getGrocery(onSuccess = {
            mView.showGroceryView(it)
        },onFailure = {})
    }

    override fun onTapDeleteGrocery(name: String) {
        mModel.deleteGrocery(name)
    }

    override fun onTapEditGrocery(name: String, description: String, amount: Int) {
        mModel.addOrUpdateGrocery(name, description, amount)
    }
}