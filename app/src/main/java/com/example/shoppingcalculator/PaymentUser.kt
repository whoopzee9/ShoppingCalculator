package com.example.shoppingcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PaymentUser(var id: Int, var payment: Double, var isPaid: Boolean): Parcelable {
    constructor() : this(0, 0.0, false)
}