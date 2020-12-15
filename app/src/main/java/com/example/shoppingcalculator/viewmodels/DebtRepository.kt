package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.PaymentUser
import com.example.shoppingcalculator.firebaseDB.FirebaseDB

class DebtRepository {
    companion object {
        var instance = DebtRepository()
    }

    var firebaseDB = FirebaseDB()

    var paymentUsers: MutableLiveData<List<PaymentUser>> = MutableLiveData()

    fun getPaymentUsers(eventName:String, callBack: (MutableList<PaymentUser>) -> Unit) {

        firebaseDB.getPaymentUsers(eventName, callBack)
    }
}