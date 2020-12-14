package com.example.shoppingcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.VKAPI.VKUser
import com.example.shoppingcalculator.VKAPI.VKUsersRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

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

        val array = IntArray(values[position].users.size)
        var users: String = ""
        var i = 0
        for (user in values[position].users) {
            array[i] = user.value.toInt()
            i++
        }

        //holder.date.text = values[position].date
        VK.execute(VKUsersRequest(array), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                var users: String = ""
                for (user in result) {
                    users += user.firstName + " " + user.lastName + ", "
                }
                users = users.dropLast(2)
                holder.users.text = users
            }
            override fun fail(error: Exception) {
            }
        })
    }

}