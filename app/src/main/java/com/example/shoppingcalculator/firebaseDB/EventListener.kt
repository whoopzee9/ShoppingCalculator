package com.example.shoppingcalculator.firebaseDB

import com.example.shoppingcalculator.viewmodels.DebtRepository
import com.google.firebase.database.ValueEventListener

class EventListener {
    companion object {
        val instance : EventListener by lazy { holder.Instance }
    }

    private object holder {
        val Instance = EventListener()
    }

    lateinit var messageListener: ValueEventListener

    fun isInitialized() = ::messageListener.isInitialized

}