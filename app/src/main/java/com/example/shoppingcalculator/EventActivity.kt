package com.example.shoppingcalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventActivity: AppCompatActivity() {

    private lateinit var rvExpenses: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        rvExpenses = findViewById(R.id.rv_expenses)

        var values = ArrayList<Expense>()
        values.add(Expense("product", "description", false))

        val adapter = EventRecyclerAdapter(values, object: EventRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, ExpensesActivity::class.java)

                //intent.putExtra("token", result)
                //intent.putExtra("username", etLogin!!.text.toString().trim())
                startActivity(intent)
            }

        })
        rvExpenses.adapter = adapter
        rvExpenses.layoutManager = LinearLayoutManager(this)
    }

    fun onTotalDebtClick(view: View) {

    }

    fun onAddExpenseClick(view: View) {

    }
}