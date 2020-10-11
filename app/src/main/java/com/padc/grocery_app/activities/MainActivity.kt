package com.padc.grocery_app.activities

import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
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
import java.io.IOException

class MainActivity : AppCompatActivity(),MainView {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mGroceryAdapter : GroceryAdapter
    private lateinit var mPresenter : MainPresenter

    companion object{
        const val PICK_IMAGE_REQUEST = 1111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpPresenter()
        setUpRecyclerView()
        setUpListener()

        mPresenter.onUiReady(this)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST || resultCode == PICK_IMAGE_REQUEST){
            if(data == null || data.data == null){
                return
            }
            val path = data.data
            try{
                path?.let {
                    if(Build.VERSION.SDK_INT == 29){
                        val source : ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver,path)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        mPresenter.onPhotoTaken(bitmap)
                    }
                    else{
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver,path
                        )
                        mPresenter.onPhotoTaken(bitmap)
                    }
                }
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
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

    override fun openGallary() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST)
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