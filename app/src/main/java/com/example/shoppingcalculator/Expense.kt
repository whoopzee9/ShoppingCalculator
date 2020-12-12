package com.example.shoppingcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class Expense(var name: String, var description: String, var isBought: Boolean, var buyer: Int, var date: String, var price: Double, var users: ArrayList<Int>): Parcelable {
}