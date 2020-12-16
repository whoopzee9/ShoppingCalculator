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

    //private lateinit var eventMessageListener: ValueEventListener
    //private lateinit var expenseMessageListener: ValueEventListener
    //private lateinit var sharingUserMessageListener: ValueEventListener
    //private lateinit var paymentUserMessageListener: ValueEventListener

    private val eventInstance = EventListener.instance
    private val expenseInstance = ExpenseListener.instance
    private val sharingUserInstance = SharingUserListener.instance
    private val paymentUserInstance = PaymentUserListener.instance

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

            eventsRef.child(eventName).setValue(Event(eventName, secretCode, Calendar.getInstance().toString(), expenses, users))
            usersRef.child(VK.getUserId().toString()).child("events").child(eventName).setValue(eventName)
            usersRef.child(eventName).addValueEventListener(eventInstance.messageListener)
        }
    }

    override fun deleteEvent(eventName: String) {
        if (eventName.isNotEmpty()) {
            eventsRef.child(eventName).removeEventListener(eventInstance.messageListener)
            eventsRef.child(eventName).removeValue()
            usersRef.child(VK.getUserId().toString()).child("events").child(eventName).removeValue()
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
        eventsRef.child(eventName).child("expences").child(expenseName).setValue(Expense(expenseName, "desc", false, VK.getUserId(), Date(System.currentTimeMillis()).toString(), price, users))
        eventsRef.child(eventName).child("expences").child(expenseName).addValueEventListener(expenseInstance.messageListener)
    }

    override fun deleteExpense(eventName: String, expenseName: String) {
        if (eventName.isNotEmpty() && expenseName.isNotEmpty()) {
            eventsRef.child(eventName).child("expences").child(expenseName).removeEventListener(expenseInstance.messageListener)
            eventsRef.child(eventName).child("expences").child(expenseName).removeValue()
        }
    }

    override fun joinExpense(eventName: String, expenseName: String) {
        if (eventName.isNotEmpty() && expenseName.isNotEmpty()) {
            eventsRef.child(eventName).child("expences").child(expenseName).child("users").child(VK.getUserId().toString()).setValue(VK.getUserId().toString())
        }
    }

    override fun exitExpense(eventName: String, expenseName: String) {
        if (eventName.isNotEmpty() && expenseName.isNotEmpty()) {
            eventsRef.child(eventName).child("expences").child(expenseName).child("users").child(VK.getUserId().toString()).removeEventListener(expenseInstance.messageListener)
            eventsRef.child(eventName).child("expences").child(expenseName).child("users").child(VK.getUserId().toString()).removeValue()
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

    override fun changeExpenseIsBought(eventName: String, expenseName: String, isBought: Boolean) {
        if (eventName.isNotEmpty() && expenseName.isNotEmpty()) {
            eventsRef.child(eventName).child("expences").child(expenseName).child("bought").setValue(isBought)
        }
    }

    override fun changeExpensePrice(eventName: String, expenseName: String, price: Double) {
        if (eventName.isNotEmpty() && expenseName.isNotEmpty()) {
            eventsRef.child(eventName).child("expences").child(expenseName).child("price").setValue(price)
        }
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

    override fun getPaymentUsers(eventName: String, callBack: (MutableList<PaymentUser>) -> Unit) {
        var users: MutableList<PaymentUser> = mutableListOf()
        usersRef.child(VK.getUserId().toString()).child("events").child(eventName).child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //println(snapshot.getValue(User::class.java))
                    for (item in snapshot.children) {
                        //println(item.value)
                        val retrieve = item.getValue(PaymentUser::class.java)
                        if (retrieve != null) {
                            users.add(retrieve)
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

    override fun setPaymentUsers(eventName: String, users: ArrayList<PaymentUser>) {
        for (item in users) {
            usersRef.child(VK.getUserId().toString()).child("events").child(eventName).child("users").child(item.id.toString()).setValue(item)
            usersRef.child(VK.getUserId().toString()).child("events").child(eventName).child("users").child(item.id.toString()).addValueEventListener(paymentUserInstance.messageListener)
        }
    }

    override fun getUsers(callBack: (MutableList<User?>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun listenEventChange(expenses: MutableList<Event>, callBack: (Event?) -> Unit) {
        if (!paymentUserInstance.isInitialized()) {
            paymentUserInstance.messageListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        println("FALSE INIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT")
                        val message = dataSnapshot.getValue(PaymentUser::class.java)
                        if (message != null) {
                            //callBack(message)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
        }
        eventInstance.messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val message = dataSnapshot.getValue(Event::class.java)
                    if (message != null) {
                        callBack(message)
                        calculateNewPaymentUsers(message.name) {
                            setPaymentUsers(message.name, it)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        // id users
        //var listUserPath = listOf(user!!.uid, "0MdOWT14PrP6eESIOtqOQ93REA62")
        var listExpenses = expenses
        for (item in listExpenses) {
            if (item != null) {
                eventsRef.child(item.name).addValueEventListener(eventInstance.messageListener)
            }
        }
    }

    override fun detachListEvents(listEvents: ArrayList<Event?>) {
        TODO("Not yet implemented")
    }

    override fun listenExpenseChange(eventName: String, expenses: MutableList<Expense>, callBack: (Expense) -> Unit) {
        if (!paymentUserInstance.isInitialized()) {
            paymentUserInstance.messageListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        println("FALSE INIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT")
                        val message = dataSnapshot.getValue(PaymentUser::class.java)
                        if (message != null) {
                            //callBack(message)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
        }

        expenseInstance.messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val message = dataSnapshot.getValue(Expense::class.java)
                    if (message != null) {
                        callBack(message)
                        calculateNewPaymentUsers(eventName) {
                            setPaymentUsers(eventName, it)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        // id users
        //var listUserPath = listOf(user!!.uid, "0MdOWT14PrP6eESIOtqOQ93REA62")
        var listExpenses = expenses
        for (item in listExpenses) {
            if (item != null) {
                eventsRef.child(eventName).child("expences").child(item.name).addValueEventListener(expenseInstance.messageListener)
            }
        }
    }

    override fun detachListExpenses(listExpenses: ArrayList<Expense?>) {
        TODO("Not yet implemented")
    }

    override fun listenSharingUsersChange(
        eventName: String,
        expenseName: String,
        users: MutableList<String>,
        callBack: (String) -> Unit
    ) {
        if (!paymentUserInstance.isInitialized()) {
            paymentUserInstance.messageListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        println("FALSE INIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT")
                        val message = dataSnapshot.getValue(PaymentUser::class.java)
                        if (message != null) {
                            //callBack(message)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
        }
        sharingUserInstance.messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //println("DATA CHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANGE")
                    val message = dataSnapshot.getValue(String::class.java)
                    if (message != null) {
                        callBack(message)
                        calculateNewPaymentUsers(eventName) {
                            setPaymentUsers(eventName, it)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        // id users
        //var listUserPath = listOf(user!!.uid, "0MdOWT14PrP6eESIOtqOQ93REA62")
        var listUsers = users
        for (item in listUsers) {
            if (item != null) {
                eventsRef.child(eventName)
                    .child("expences")
                    .child(expenseName)
                    .child("users")
                    .child(item).addValueEventListener(sharingUserInstance.messageListener)
            }
        }
    }

    override fun detachListSharingUsers(listExpenses: ArrayList<String>) {

    }

    override fun listenPaymentUsersChange(
        eventName: String,
        users: MutableList<PaymentUser>,
        callBack: (PaymentUser) -> Unit
    ) {
        paymentUserInstance.messageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    println("TRUE INIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT")
                    val message = dataSnapshot.getValue(PaymentUser::class.java)
                    if (message != null) {
                        callBack(message)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        // id users
        //var listUserPath = listOf(user!!.uid, "0MdOWT14PrP6eESIOtqOQ93REA62")
        //TODO возможно добавить здесь слушателя сразу для всей ветки Users
        var listUsers = users
        for (item in listUsers) {
            if (item != null) {
                usersRef.child(VK.getUserId().toString())
                    .child("events")
                    .child(eventName)
                    .child("users")
                    .child(item.id.toString()).addValueEventListener(paymentUserInstance.messageListener)
            }
        }
    }

    override fun detachListPaymentUsers(listExpenses: ArrayList<PaymentUser>) {
        TODO("Not yet implemented")
    }



    fun calculateNewPaymentUsers(eventName: String, callBack: (ArrayList<PaymentUser>) -> Unit) {
        getExpenses(eventName) {
            var users = ArrayList<PaymentUser>()

            for (item in it) {
                var payment = item.price / item.users.size
                var isPresent: Boolean = false
                var isSharing: Boolean = false
                for (user in item.users) {
                    if (user.value == VK.getUserId().toString()) {
                        isSharing = true
                    }
                }
                if (!isSharing) {
                    continue
                }
                for (i in 0 until users.size) {
                    if (item.buyer == users[i].id) {
                        isPresent = true
                        users[i].payment += payment
                    }
                }
                if (!isPresent && item.buyer != VK.getUserId()) {
                    users.add(PaymentUser(item.buyer, payment, false))
                }
            }
            callBack(users)
        }
    }
}