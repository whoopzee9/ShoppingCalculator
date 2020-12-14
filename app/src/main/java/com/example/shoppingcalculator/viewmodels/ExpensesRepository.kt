package com.example.shoppingcalculator.viewmodels

import com.example.shoppingcalculator.Expense
import com.example.shoppingcalculator.firebaseDB.FirebaseDB

class ExpensesRepository {
    companion object {
        var instance = ExpensesRepository()
    }

    var firebaseDB = FirebaseDB()

    fun getExpenses(eventName:String, callBack: (MutableList<Expense>) -> Unit) {
        //TODO somehow retrieve data from DB
        //val current = EventRepository.instance
        if (eventName.isNotEmpty()) {
            firebaseDB.getExpenses(eventName, callBack)
        }
    }
}