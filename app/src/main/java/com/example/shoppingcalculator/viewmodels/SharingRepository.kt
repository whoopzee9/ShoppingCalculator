package com.example.shoppingcalculator.viewmodels

import com.example.shoppingcalculator.Expense
import com.example.shoppingcalculator.firebaseDB.FirebaseDB

class SharingRepository {
    companion object {
        var instance = SharingRepository()
    }

    var firebaseDB = FirebaseDB()

    fun getSharingUsers(eventName:String, expenseName:String, callBack: (MutableList<String>) -> Unit) {
        //TODO somehow retrieve data from DB
        //val current = EventRepository.instance
        if (eventName.isNotEmpty()) {
            firebaseDB.getSharingUsers(eventName, expenseName, callBack)
        }
    }
}