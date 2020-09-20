package com.padc.grocery_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.grocery.data.vos.GroceryVO
import com.padc.grocery_app.R
import com.padc.grocery_app.adapters.GroceryAdapter
import com.padc.grocery_app.fragments.GroceryDialogFragment
import com.padc.grocery_app.mvp.presenters.MainPresenter
import com.padc.grocery_app.mvp.presenters.impls.MainPresenterImpl
import com.padc.grocery_app.mvp.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MainView {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mGroceryAdapter : GroceryAdapter
    private lateinit var mPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpPresenter()
        setUpRecyclerView()
        setUpListener()

        mPresenter.onUiReady(this)
    }

    override fun showGroceryView(groceryList: List<GroceryVO>) {
        mGroceryAdapter.setNewData(groceryList)
    }

    override fun showGroceryDialog(name: String, description: String, amount: String) {
        val groceryDialog = GroceryDialogFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(GroceryDialogFragment.BUNDLE_NAME,name)
        bundle.putString(GroceryDialogFragment.BUNDLE_DESCRIPTION,description)
        bundle.putString(GroceryDialogFragment.BUNDLE_AMOUNT,amount)
        groceryDialog.arguments = bundle
        groceryDialog.show(supportFragmentManager,GroceryDialogFragment.TAG_ADD_GROCERY_DIALOG)
    }

    private fun setUpRecyclerView(){
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mGroceryAdapter = GroceryAdapter(mPresenter)
        rViewGrocery.layoutManager = linearLayoutManager
        rViewGrocery.adapter = mGroceryAdapter
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    private fun setUpListener(){
        fab.setOnClickListener {
            GroceryDialogFragment.newInstance()
                .show(supportFragmentManager,GroceryDialogFragment.TAG_ADD_GROCERY_DIALOG)
        }
    }

}