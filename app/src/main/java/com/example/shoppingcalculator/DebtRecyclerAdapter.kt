package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DebtRecyclerAdapter(var values: ArrayList<PaymentUser>, var onClickListener: OnClickListener): RecyclerView.Adapter<DebtRecyclerViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.debt_row_layout, parent, false)
        return DebtRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: DebtRecyclerViewHolder, position: Int) {
        holder.username.text = values[position].name

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }

        holder.payment.text = values[position].payment
        holder.checkBox.isChecked = values[position].isPaid
    }

}
