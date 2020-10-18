package com.padc.grocery_app.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

object FireBaseAuthApiImpl : FireBaseAuthApi {

    private val mFirebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful && it.isComplete){
                onSuccess()
            } else{
                onError(it.exception?.message ?: "Please check your internet connection!")
            }
        }
    }

    override fun register(
        email: String,
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                mFirebaseAuth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(username).build()
                )
                onSuccess()
            } else {
                onError(it.exception?.message ?: "Please check internet connection")
            }
        }
    }

    override fun getUsername() : String {
        return mFirebaseAuth.currentUser?.displayName ?: ""
    }
}