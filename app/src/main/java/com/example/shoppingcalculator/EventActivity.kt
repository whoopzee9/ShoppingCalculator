package com.example.shoppingcalculator

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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


                startActivity(intent)
            }

        })
        rvExpenses.adapter = adapter
        rvExpenses.layoutManager = LinearLayoutManager(this)
    }

    fun onTotalDebtClick(view: View) {
        val intent = Intent(applicationContext, DebtActivity::class.java)


        startActivity(intent)
    }

    fun onAddExpenseClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.expense_dialog_layout, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Добавление нового рассхода")
            .setView(placeFormView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Добавить", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //TODO добавление нового продукта
            dialog.dismiss()
        }
    }
}