package com.example.shoppingcalculator

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.security.InvalidParameterException

@Parcelize
class PaymentUser(var id: Int, var payment: Double, var isPaid: Boolean): Parcelable {
    constructor() : this(0, 0.0, false)
    init {
        if ((payment < 0) or (id < 0)) {
            throw InvalidParameterException("Invalid parameter")
        }
    }
}