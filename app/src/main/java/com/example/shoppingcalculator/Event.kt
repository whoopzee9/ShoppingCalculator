package com.example.shoppingcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Parcelize
class Event(var name:String, var code:String, var date: String, var expenses: ArrayList<Expense>, var users:HashMap<String, String>): Parcelable {
    constructor() : this("", "", "", ArrayList(), HashMap())
}