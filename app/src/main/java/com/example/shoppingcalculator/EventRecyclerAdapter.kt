package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EventRecyclerAdapter(var values: ArrayList<Expense>, var onClickListener: OnClickListener): RecyclerView.Adapter<EventRecyclerViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onCheckBoxClick(position: Int, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.event_row_layout, parent, false)
        return EventRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: EventRecyclerViewHolder, position: Int) {
        holder.title.text = values[position].name

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }

        holder.description.text = "Всего: " + values[position].price.toString() + " руб."
        holder.checkBox.isChecked = values[position].isBought

        holder.checkBox.setOnClickListener {
            onClickListener.onCheckBoxClick(position, holder.checkBox.isChecked)
        }
    }

}