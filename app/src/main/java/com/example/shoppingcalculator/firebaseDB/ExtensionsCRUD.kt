package com.example.shoppingcalculator.firebaseDB

import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.Expense
import com.example.shoppingcalculator.PaymentUser

interface ExtensionsCRUD {

    fun createEvent(eventName: String, secretCode: String)
    fun joinEvent(eventName: String)
    fun getEvents(callBack: (MutableList<String?>) -> Unit)

    fun createExpense(expenseName: String, price: Double)
    fun joinExpense(expenseName: String)
    fun getExpenses(callBack: (MutableList<String?>) -> Unit)

    fun createUser()
    fun getUsers(callBack: (MutableList<PaymentUser?>) -> Unit) //TODO Возможно поменять класс??

    fun listenEventChange(users: ArrayList<String?>, callBack: ((Event?) -> Unit))
    fun detachListEvents(listEvents: ArrayList<Event?>)

    fun listenExpenseChange(users: ArrayList<String?>, callBack: ((Expense?) -> Unit))
    fun detachListExpenses(listExpenses: ArrayList<Expense?>)
}