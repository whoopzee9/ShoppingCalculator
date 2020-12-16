package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.PaymentUser

class DebtViewModel : ViewModel() {

    var repository = DebtRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        repository.paymentUsers = MutableLiveData()
        //updateEvent()

    }

    fun getUsers(): LiveData<List<PaymentUser>> {
        return repository.paymentUsers
    }

    fun updatePaymentUsers(eventName: String) {
        //println("before updating group")
        repository.getPaymentUsers(eventName) {
            println("Updating payment users")
            repository.paymentUsers.postValue(it)
        }
    }

    fun setUsers(data: List<PaymentUser>) {
        repository.paymentUsers.value = data
    }

    //test
    fun listenChange(eventName: String) {
        updatePaymentUsers(eventName)
    }
}