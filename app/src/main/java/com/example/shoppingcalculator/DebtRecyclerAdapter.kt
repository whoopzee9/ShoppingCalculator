package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.VKAPI.VKUser
import com.example.shoppingcalculator.VKAPI.VKUsersRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

class DebtRecyclerAdapter(var values: ArrayList<PaymentUser>, var onClickListener: OnClickListener): RecyclerView.Adapter<DebtRecyclerViewHolder>() {
    interface OnClickListener {
        fun onItemClick(position: Int)
        fun onCheckBoxClick(position: Int, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.debt_row_layout, parent, false)
        return DebtRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onBindViewHolder(holder: DebtRecyclerViewHolder, position: Int) {

        val array = intArrayOf(values[position].id)
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

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }

        holder.payment.text = values[position].payment.toString() + " руб."
        holder.checkBox.isChecked = values[position].isPaid

        holder.checkBox.setOnClickListener {
            onClickListener.onCheckBoxClick(position, holder.checkBox.isChecked)
        }
    }

}
