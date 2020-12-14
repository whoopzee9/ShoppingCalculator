package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Expense

class SharingViewModel: ViewModel() {
    var users: MutableLiveData<List<String>> = MutableLiveData()
    var repository = SharingRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        users = MutableLiveData()
        //updateExpenses()
    }

    fun getSharingUsers(): LiveData<List<String>> {
        return users
    }

    fun updateUsers(eventName: String, expenseName: String) {
        //println("before updating group")
        repository.getSharingUsers(eventName, expenseName) {
            println("--------------------------------------------------------------------------------------------------------------------------------")
            users.postValue(it)

        }
    }

    fun setUsers(data: List<String>) {
        users.value = data
    }

    //test
    fun listenChange(eventName: String, expenseName: String) {
        updateUsers(eventName, expenseName)
    }
}