package com.example.shoppingcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(var events: ArrayList<Event>, var name: String): Parcelable {
}