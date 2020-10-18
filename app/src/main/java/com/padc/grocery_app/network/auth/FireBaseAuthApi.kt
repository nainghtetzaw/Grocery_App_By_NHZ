package com.padc.grocery_app.network.auth

interface FireBaseAuthApi {
    fun login(email : String, password : String, onSuccess : () -> Unit, onError : (String) -> Unit)
    fun register(email : String , username : String, password : String, onSuccess: () -> Unit,onError: (String) -> Unit)
    fun getUsername() : String
}