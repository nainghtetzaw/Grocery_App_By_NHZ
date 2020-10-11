package com.padc.grocery_app.network

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padc.grocery.data.vos.GroceryVO
import java.io.ByteArrayOutputStream
import java.util.*

object FIrestoreApiImpl : FirebaseApi {

    private val db = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReferance = storage.reference

    override fun getGrocery(
        onSuccess: (groceries: List<GroceryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        db.collection("groceries")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.message ?: "Please Check Internet Connection.")
                } ?: run {
                    val groceriesList : MutableList<GroceryVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()

                    for(document in result){
                        val grocery = GroceryVO()
                        val data = document.data

                        grocery.image = data?.get("image") as String
                        grocery.amount = (data["amount"] as Long).toInt()
                        grocery.description = data["description"] as String
                        grocery.name = data["name"] as String

                        groceriesList.add(grocery)
                    }
                    onSuccess(groceriesList)
                }
            }
    }

    override fun addAndUpdateGrocery(
        name: String,
        description: String,
        amount: Int,
        image: String
    ) {
        val groceryMap = hashMapOf(
            "name" to name,
            "description" to description,
            "amount" to amount.toLong(),
            "image" to image
        )

        db.collection("groceries")
            .document(name)
            .set(groceryMap)
            .addOnSuccessListener { Log.d("Success", "Successfully added grocery") }
            .addOnFailureListener { Log.d("Failure", "Failed to add grocery") }
    }

    override fun deleteGrocery(name: String) {
        db.collection("groceries")
            .document(name)
            .delete()
            .addOnSuccessListener { Log.d("Success", "Successfully deleted grocery") }
            .addOnFailureListener { Log.d("Failure", "Failed to delete grocery") }
    }

    override fun uploadGrocery(grocery: GroceryVO, image: Bitmap) {
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
            }

            addAndUpdateGrocery(
                grocery.name ?: "",
                grocery.description ?: "",
                grocery.amount ?: 0,
                imageUrl ?: ""
            )
        }
    }
}