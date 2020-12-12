package com.example.shoppingcalculator.firebaseDB

import com.example.shoppingcalculator.Event
import com.example.shoppingcalculator.Expense
import com.example.shoppingcalculator.PaymentUser
import com.example.shoppingcalculator.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.requests.VKRequest
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

    override fun createExpense(eventName: String, expenseName: String, price: Double) {
        val users = ArrayList<User>()
        //users.add(User(, ))
        //eventsRef.child(eventName).child("expences").setValue(Expense(expenseName, "desc", false, VK.getUserId().toString(), Date(System.currentTimeMillis()), price, ))
    }

    override fun joinExpense(expenseName: String) {
        TODO("Not yet implemented")
    }

    override fun getExpenses(callBack: (MutableList<String?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun createUser() {
        //TODO отправлять запрос на сервера ВК чтобы получить имя текущего пользователя
        println("created")
        val user = User(ArrayList(), "Бубляев Алексей")
        usersRef.child(VK.getUserId().toString()).setValue(user)
    }

    override fun getUser(userId: String, callBack: (User?) -> Unit) {
        var user :User
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //user = snapshot.children.
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun getUsers(callBack: (MutableList<User?>) -> Unit) {
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