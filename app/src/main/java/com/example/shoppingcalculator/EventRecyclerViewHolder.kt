package com.example.shoppingcalculator

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
    var title: TextView = itemView.findViewById(R.id.tv_expense_title)
    var description: TextView = itemView.findViewById(R.id.tv_description)
    var checkBox: CheckBox = itemView.findViewById(R.id.cb_checkBox)
}