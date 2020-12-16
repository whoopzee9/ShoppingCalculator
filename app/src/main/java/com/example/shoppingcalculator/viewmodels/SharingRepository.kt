package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.shoppingcalculator.Expense
import com.example.shoppingcalculator.firebaseDB.FirebaseDB

class SharingRepository {
    companion object {
        val instance : SharingRepository by lazy { holder.Instance }
    }

    private object holder {
        val Instance = SharingRepository()
    }

    var firebaseDB = FirebaseDB()
    var users: MutableLiveData<List<String>> = MutableLiveData()

    fun getSharingUsers(eventName:String, expenseName:String, callBack: (MutableList<String>) -> Unit) {
        //TODO somehow retrieve data from DB
        //val current = EventRepository.instance
        if (eventName.isNotEmpty()) {
            firebaseDB.getSharingUsers(eventName, expenseName, callBack)
        }
    }
}