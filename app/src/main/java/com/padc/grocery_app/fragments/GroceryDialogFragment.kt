package com.padc.grocery_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.padc.grocery_app.R
import com.padc.grocery_app.mvp.presenters.MainPresenter
import com.padc.grocery_app.mvp.presenters.impls.MainPresenterImpl
import kotlinx.android.synthetic.main.fragment_dialog.*
import kotlinx.android.synthetic.main.fragment_dialog.view.*


class GroceryDialogFragment : DialogFragment() {

    companion object {
        const val TAG_ADD_GROCERY_DIALOG = "TAG_ADD_GROCERY_DIALOG"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        const val BUNDLE_DESCRIPTION = "BUNDLE_DESCRIPTION"
        const val BUNDLE_AMOUNT = "BUNDLE_AMOUNT"

        fun newInstance() : GroceryDialogFragment {
            return GroceryDialogFragment()
        }
    }

    private lateinit var mPresenter : MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()

        view.etGroceryName?.setText(arguments?.getString(BUNDLE_NAME))
        view.etGroceryDescription?.setText(arguments?.getString(BUNDLE_DESCRIPTION))
        view.etGroceryAmount?.setText(arguments?.getString(BUNDLE_AMOUNT))

        view.btnAdd.setOnClickListener {
            mPresenter.onTapAddGrocery(
                etGroceryName.text.toString(),
                etGroceryDescription.text.toString(),
                etGroceryAmount.text.toString().toInt()
            )
            dismiss()
        }
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
    }

}