 package com.example.shoppingcalculator

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.firebaseDB.FirebaseDB
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

 class EventActivity: AppCompatActivity() {

    private lateinit var rvExpenses: RecyclerView
     private var firebaseDB = FirebaseDB()
     private lateinit var currEvent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        rvExpenses = findViewById(R.id.rv_expenses)

        currEvent = intent.getStringExtra("currEvent")

        var values = ArrayList<Expense>()
        values.add(Expense("product", "description", false, 123, Date(System.currentTimeMillis()).toString(),123.0, ArrayList()))

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
            var name = placeFormView.findViewById<EditText>(R.id.et_expense_name).text
            var cost = placeFormView.findViewById<EditText>(R.id.et_expense_cost).text
            if ((name.isBlank()) || (cost.isBlank())) {
                if (name.isBlank()) {
                    placeFormView.findViewById<EditText>(R.id.et_expense_name).hint = "Введите имя"
                }
                if (cost.isBlank()) {
                    placeFormView.findViewById<EditText>(R.id.et_expense_cost).hint =
                        "Введите стоимость"
                }
            } else {
                val maybeDouble = cost.toString().toDoubleOrNull()
                placeFormView.findViewById<EditText>(R.id.et_expense_cost).setText("")
                if (maybeDouble == null) {
                    placeFormView.findViewById<EditText>(R.id.et_expense_cost).hint =
                        "Введите число"
                } else {
                    firebaseDB.createExpense(currEvent, name.toString(), maybeDouble)
                    dialog.dismiss()
                }
            }
        }
    }
}