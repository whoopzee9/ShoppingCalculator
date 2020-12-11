package com.example.shoppingcalculator.firebaseDB

import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.Expense
import com.example.shoppingcalculator.PaymentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FirebaseDB : ExtensionsCRUD {
    private var dataRef = FirebaseDatabase.getInstance().reference
    private var eventsRef = FirebaseDatabase.getInstance().getReference("app/events")
    private var usersRef = FirebaseDatabase.getInstance().getReference("app/users")

    //Auth
    //private var mAuth = FirebaseAuth.getInstance()

    private lateinit var eventMessageListener: ValueEventListener
    private lateinit var expenseMessageListener: ValueEventListener

    //Authenticated user
    //var user = mAuth.currentUser
    fun init() {
        //eventsRef.child("event").child("expense1").
    }

    override fun createEvent(eventName: String, secretCode: String) {
        TODO("Not yet implemented")
    }

    override fun joinEvent(eventName: String) {
        TODO("Not yet implemented")
    }

    override fun getEvents(callBack: (MutableList<String?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun createExpense(expenseName: String, price: Double) {
        TODO("Not yet implemented")
    }

    override fun joinExpense(expenseName: String) {
        TODO("Not yet implemented")
    }

    override fun getExpenses(callBack: (MutableList<String?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun createUser() {
        TODO("Not yet implemented")
    }

    override fun getUsers(callBack: (MutableList<PaymentUser?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun listenEventChange(users: ArrayList<String?>, callBack: (Event?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun detachListEvents(listEvents: ArrayList<Event?>) {
        TODO("Not yet implemented")
    }

    override fun listenExpenseChange(users: ArrayList<String?>, callBack: (Expense?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun detachListExpenses(listExpenses: ArrayList<Expense?>) {
        TODO("Not yet implemented")
    }


}