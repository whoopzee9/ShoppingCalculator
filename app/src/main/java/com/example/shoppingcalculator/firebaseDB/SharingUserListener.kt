package com.example.shoppingcalculator.firebaseDB

import com.google.firebase.database.ValueEventListener

class SharingUserListener {
    companion object {
        val instance : SharingUserListener by lazy { holder.Instance }
    }

    private object holder {
        val Instance = SharingUserListener()
    }

    lateinit var messageListener: ValueEventListener

    fun isInitialized() = ::messageListener.isInitialized
}