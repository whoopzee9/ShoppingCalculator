package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.VKAPI.VKUser
import com.example.shoppingcalculator.VKAPI.VKUsersRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

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
        val array = intArrayOf(values[position].toInt())

        //holder.date.text = values[position].date
        VK.execute(VKUsersRequest(array), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                var users: String = ""
                for (user in result) {
                    users += user.firstName + " " + user.lastName
                }
                holder.username.text = users
            }
            override fun fail(error: Exception) {
            }
        })
        //holder.username.text = values[position]//.name

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }

        //holder.checkBox.isChecked = values[position].isPaid
    }

}