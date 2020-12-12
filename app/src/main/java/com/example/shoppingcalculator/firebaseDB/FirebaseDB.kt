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
import com.google.firebase.database.ktx.getValue
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
        if (eventName.isNotEmpty()) {
            val users = HashMap<String, String>()
            users.put(VK.getUserId().toString(), VK.getUserId().toString())
            val expenses = ArrayList<Expense>()
            eventsRef.child(eventName).setValue(Event(eventName, secretCode, expenses, users))
            usersRef.child(VK.getUserId().toString()).child("events").child(eventName).setValue(eventName)
        }
    }

    override fun joinEvent(code: String) {
        getEvents {
            if (code.isNotEmpty()) {
                for (item in it) {
                    if (item.code.equals(code)) {
                        eventsRef.child(item.name).child("users").child(VK.getUserId().toString()).setValue(VK.getUserId().toString())
                        usersRef.child(VK.getUserId().toString()).child("events").child(item.name).setValue(item.name)
                    }
                }
            }
        }
    }

    override fun getEvents(callBack: (MutableList<Event>) -> Unit) {
        var events: MutableList<Event> = mutableListOf()
        eventsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //println(snapshot.getValue(User::class.java))
                    for (item in snapshot.children) {
                        println(item.value)
                        val retrieveEvent = item.getValue(Event::class.java)
                        if (retrieveEvent != null) {
                            events.add(retrieveEvent)
                        }
                    }
                    callBack(events)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun createExpense(eventName: String, expenseName: String, price: Double) {
        val users = HashMap<String, String>()
        users.put(VK.getUserId().toString(), VK.getUserId().toString())
        eventsRef.child(eventName).child("expences").child("expenseID").setValue(Expense(expenseName, "desc", false, VK.getUserId(), Date(System.currentTimeMillis()).toString(), price, users))

    }

    override fun joinExpense(eventName: String, expenseName: String) {
        if (eventName.isNotEmpty() && expenseName.isNotEmpty()) {
            eventsRef.child(eventName).child("expences").child(expenseName).child("users").child(VK.getUserId().toString()).setValue(VK.getUserId().toString())
        }
    }

    override fun getExpenses(eventName:String, callBack: (MutableList<Expense>) -> Unit) {
        var expenses: MutableList<Expense> = mutableListOf()
        eventsRef.child(eventName).child("expences").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //println(snapshot.getValue(User::class.java))
                    for (item in snapshot.children) {
                        println(item.value)
                        val retrieveExpense = item.getValue(Expense::class.java)
                        if (retrieveExpense != null) {
                            expenses.add(retrieveExpense)
                        }
                    }
                    callBack(expenses)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getSharingUsers(
        eventName: String,
        expenseName: String,
        callBack: (MutableList<String>) -> Unit
    ) {
        var users: MutableList<String> = mutableListOf()
        eventsRef.child(eventName).child("expences").child(expenseName).child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //println(snapshot.getValue(User::class.java))
                    for (item in snapshot.children) {
                        println(item.value)
                        //val retrieveExpense = item.getValue(Expense::class.java)
                        if (item != null) {
                            users.add(item.value.toString())
                        }
                    }
                    callBack(users)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    override fun createUser() {
        //TODO отправлять запрос на сервера ВК чтобы получить имя текущего пользователя
        val user = User(ArrayList(), "Бубляев Алексей")
        usersRef.child(VK.getUserId().toString()).setValue(user)
    }

    override fun getUser(userId: String, callBack: (MutableList<User>) -> Unit) {
        /*var user: MutableList<User> = mutableListOf()
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    println(snapshot.getValue(User::class.java))

                    for (item in snapshot.children) {
                        println(item.value)
                        /*val retrieveUser = item.getValue(User::class.java)
                        if (retrieveUser != null) {
                            user.add(retrieveUser)
                        }*/
                    }
                    callBack(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/

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