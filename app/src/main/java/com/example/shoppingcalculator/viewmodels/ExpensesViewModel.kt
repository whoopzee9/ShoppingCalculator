package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.Expense

class ExpensesViewModel: ViewModel() {

    var repository = ExpensesRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        repository.expenses = MutableLiveData()
        //updateExpenses()
    }

    fun getExpenses(): LiveData<List<Expense>> {
        return repository.expenses
    }

    fun updateExpenses(eventName: String) {
        //println("before updating group")
        repository.getExpenses(eventName) {
            println("updating expenses")
            repository.expenses.postValue(it)

        }
    }

    fun setExpenses(data: List<Expense>) {
        repository.expenses.value = data
    }

    //test
    fun listenChange(eventName: String) {
        updateExpenses(eventName)
    }
}