package com.example.shoppingcalculator

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpensesRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
    var username: TextView = itemView.findViewById(R.id.tv_sharing_user_name)
    var checkBox: CheckBox = itemView.findViewById(R.id.cb_sharing_checkBox)
}
