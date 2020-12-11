package com.example.shoppingcalculator.viewmodels

class ExpensesRepository {
    companion object {
        var instance = ExpensesRepository()
    }

    //var mFirebaseDB = FirebaseDB()

    /*fun getGroupFromDB(callBack: (MutableList<User?>) -> Unit) {
        //TODO somehow retrieve data from DB
        val current = CurrentGroup.instance
        if (current.groupName.isNotEmpty()) {
            mFirebaseDB.getUsersFromGroup(current.groupName, callBack)
        }
    }*/
}