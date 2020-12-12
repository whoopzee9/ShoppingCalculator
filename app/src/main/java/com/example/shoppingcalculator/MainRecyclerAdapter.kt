package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainRecyclerAdapter(var values: ArrayList<Event>, var onClickListener: OnClickListener): RecyclerView.Adapter<MainRecyclerViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_row_layout, parent, false)
        return MainRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        holder.title.text = values[position].name

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }

        var users: String = ""
        for (user in values[position].users) {
            users += "$user, "
        }
        users = users.dropLast(2)
        holder.users.text = users
        //holder.date.text = values[position].date
    }

}