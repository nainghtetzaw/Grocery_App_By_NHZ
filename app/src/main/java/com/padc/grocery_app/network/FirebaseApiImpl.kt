package com.padc.grocery_app.network

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.padc.grocery.data.vos.GroceryVO

object FirebaseApiImpl : FirebaseApi {

    private val database : DatabaseReference = Firebase.database.reference

    override fun getGrocery(
        onSuccess: (groceries: List<GroceryVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("grocery-app-5343f").child("groceries").addValueEventListener(object : ValueEventListener{
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

    override fun addAndUpdateGrocery(name: String, description: String, amount: Int) {
        database.child("grocery-app-5343f").child("groceries").child(name).setValue(GroceryVO(name,description,amount))
    }

    override fun deleteGrocery(name: String) {
        database.child("grocery-app-5343f").child("groceries").child(name).removeValue()
    }
}