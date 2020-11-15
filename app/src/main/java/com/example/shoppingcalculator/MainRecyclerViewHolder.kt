package com.example.shoppingcalculator

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
    var title: TextView = itemView.findViewById(R.id.tv_title)
    var users: TextView = itemView.findViewById(R.id.tv_users)
    var date: TextView = itemView.findViewById(R.id.tv_date)
}