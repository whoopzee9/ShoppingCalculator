package com.example.shoppingcalculator.firebaseDB

import com.google.firebase.database.ValueEventListener

class ExpenseListener {
    companion object {
        val instance : ExpenseListener by lazy { holder.Instance }
    }

    private object holder {
        val Instance = ExpenseListener()
    }

    lateinit var messageListener: ValueEventListener

    fun isInitialized() = ::messageListener.isInitialized

}