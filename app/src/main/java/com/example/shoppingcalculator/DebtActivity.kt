package com.example.shoppingcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DebtActivity: AppCompatActivity() {

    private lateinit var rvDebtList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt)

        rvDebtList = findViewById(R.id.rv_debt_list)

        var values = ArrayList<PaymentUser>()
        values.add(PaymentUser("Alex", "40 гривен", false))

        val adapter = DebtRecyclerAdapter(values, object: DebtRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                //val intent = Intent(applicationContext, ExpensesActivity::class.java)


                //startActivity(intent)
            }

        })
        rvDebtList.adapter = adapter
        rvDebtList.layoutManager = LinearLayoutManager(this)
    }
}