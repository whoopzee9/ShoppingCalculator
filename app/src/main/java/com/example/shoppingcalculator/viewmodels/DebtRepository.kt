package com.example.shoppingcalculator.viewmodels

class DebtRepository {
    companion object {
        var instance = DebtRepository()
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