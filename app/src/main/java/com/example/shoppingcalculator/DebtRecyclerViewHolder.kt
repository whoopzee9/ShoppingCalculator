package com.example.shoppingcalculator

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DebtRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
    var username: TextView = itemView.findViewById(R.id.tv_username)
    var payment: TextView = itemView.findViewById(R.id.tv_payment_amount)
    var checkBox: CheckBox = itemView.findViewById(R.id.cb_payment_checkBox)
}
