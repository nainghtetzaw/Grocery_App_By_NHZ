package com.padc.grocery_app.fragments

import android.content.ContentProvider
import android.content.ContentProviderClient
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.padc.grocery_app.R
import com.padc.grocery_app.activities.MainActivity
import com.padc.grocery_app.mvp.presenters.MainPresenter
import com.padc.grocery_app.mvp.presenters.impls.MainPresenterImpl
import kotlinx.android.synthetic.main.fragment_dialog.*
import kotlinx.android.synthetic.main.fragment_dialog.view.*
import java.io.IOException


class GroceryDialogFragment : DialogFragment() {

    companion object {
        const val TAG_ADD_GROCERY_DIALOG = "TAG_ADD_GROCERY_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"
        const val BUNDLE_IMAGE = "BUNDLE_IMAGE"
        const val PICK_IMAGE_REQUEST = 1111

        fun newInstance() : GroceryDialogFragment {
            return GroceryDialogFragment()
        }
    }

    private lateinit var mPresenter : MainPresenter
    private var imageUrl : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()

        arguments?.let {
            view.etGroceryName?.setText(it.getString(BUNDLE_NAME))
            view.etGroceryDescription?.setText(it.getString(BUNDLE_DESCRIPTION))
            view.etGroceryAmount?.setText(it.getString(BUNDLE_AMOUNT))

            if(it.getString(BUNDLE_IMAGE) == "" || it.getString(BUNDLE_IMAGE) != null){
                Glide.with(this)
                    .load(arguments?.getString(BUNDLE_IMAGE))
                    .into(imgAdd)
            }
        }


        view.imgAdd.setOnClickListener {
            openGallery()
        }

        view.btnAdd.setOnClickListener {

            mPresenter.onTapAddGrocery(
                etGroceryName.text.toString(),
                etGroceryDescription.text.toString(),
                etGroceryAmount.text.toString().toInt(),
                imageUrl
            )

            dismiss()
        }
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
                        activity?.let {
                            val source : ImageDecoder.Source = ImageDecoder.createSource(it.applicationContext.contentResolver,path)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            
                            mPresenter.onPhotoTaken(bitmap,onSuccess = {

                                Glide.with(this)
                                    .load(it)
                                    .into(imgAdd)

                                imageUrl = it
                            })
                        }
                    }
                    else{
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.applicationContext?.contentResolver,path
                        )
                        mPresenter.onPhotoTaken(bitmap,onSuccess = {

                            Glide.with(this)
                                .load(it)
                                .into(imgAdd)

                            imageUrl = it
                        })
                    }
                }
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }

    private fun openGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
    }

}