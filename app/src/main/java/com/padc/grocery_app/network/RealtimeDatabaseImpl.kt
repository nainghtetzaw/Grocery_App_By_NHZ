package com.padc.grocery_app.network

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padc.grocery.data.vos.GroceryVO
import java.io.ByteArrayOutputStream
import java.util.*

object RealtimeDatabaseImpl : FirebaseApi {

    private val database : DatabaseReference = Firebase.database.reference
    private val storage = FirebaseStorage.getInstance()
    private val storageReferance = storage.reference

    override fun getGrocery(
        onSuccess: (groceries: List<GroceryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("groceries").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val groceryList = arrayListOf<GroceryVO>()
                snapshot.children.forEach{dataSnapshot ->
                    dataSnapshot.getValue(GroceryVO::class.java)?.let {
                        groceryList.add(it)
                    }
                }
                onSuccess(groceryList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }

        })
    }

    override fun addAndUpdateGrocery(name: String, description: String, amount: Int,image: String) {
        database.child("groceries").child(name).setValue(GroceryVO(name,description,amount,image))
    }

    override fun deleteGrocery(name: String) {
        database.child("groceries").child(name).removeValue()
    }

    override fun uploadGrocery(image: Bitmap,onSuccess : (imageUrl : String) -> Unit) {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()

        val imageRef = storageReferance.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener{

        }.addOnSuccessListener {

        }
        val urlTask = uploadTask.continueWithTask{
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener{task ->
            val imageUrl = task.result?.toString()
            imageUrl?.let {
                Log.e("URL",it)
                onSuccess(it)
            }
        }

    }
}