package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExpensesRecyclerAdapter(var values: ArrayList<String>, var onClickListener: OnClickListener): RecyclerView.Adapter<ExpensesRecyclerViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.expenses_row_layout, parent, false)
        return ExpensesRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: ExpensesRecyclerViewHolder, position: Int) {
        holder.username.text = values[position]//.name

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }

        //holder.checkBox.isChecked = values[position].isPaid
    }

}