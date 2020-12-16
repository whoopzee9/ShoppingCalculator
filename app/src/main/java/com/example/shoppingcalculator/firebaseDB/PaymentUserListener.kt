package com.example.shoppingcalculator.firebaseDB

import com.google.firebase.database.ValueEventListener

class PaymentUserListener {
    companion object {
        val instance : PaymentUserListener by lazy { holder.Instance }
    }

    private object holder {
        val Instance = PaymentUserListener()
    }

    lateinit var messageListener: ValueEventListener

    fun isInitialized() = ::messageListener.isInitialized
}