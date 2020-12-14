package com.example.shoppingcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.Expense

class ExpensesViewModel: ViewModel() {
    var expenses: MutableLiveData<List<Expense>> = MutableLiveData()
    var repository = ExpensesRepository.instance

    fun init() {
        //repository = GroupRepository.instance
        expenses = MutableLiveData()
        //updateExpenses()
    }

    fun getExpenses(): LiveData<List<Expense>> {
        return expenses
    }

    fun updateExpenses(eventName: String) {
        //println("before updating group")
        repository.getExpenses(eventName) {
            println("updating group")
            expenses.postValue(it)

        }
    }

    fun setExpenses(data: List<Expense>) {
        expenses.value = data
    }

    //test
    fun listenChange(eventName: String) {
        updateExpenses(eventName)
    }
}