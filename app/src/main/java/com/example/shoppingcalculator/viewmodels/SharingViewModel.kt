package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Expense

class SharingViewModel: ViewModel() {

    var repository = SharingRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        repository.users = MutableLiveData()
        //updateExpenses()
    }

    fun getSharingUsers(): LiveData<List<String>> {
        return repository.users
    }

    fun updateUsers(eventName: String, expenseName: String) {
        //println("before updating group")
        repository.getSharingUsers(eventName, expenseName) {
            println("updating sharing users")
            repository.users.postValue(it)

        }
    }

    fun setUsers(data: List<String>) {
        repository.users.value = data
    }

    //test
    fun listenChange(eventName: String, expenseName: String) {
        updateUsers(eventName, expenseName)
    }
}