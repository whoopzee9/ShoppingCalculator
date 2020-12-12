package com.example.shoppingcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Event(var name:String, var expenses: ArrayList<Expense>, var users:ArrayList<User>): Parcelable {
}