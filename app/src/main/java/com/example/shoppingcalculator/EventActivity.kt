 package com.example.shoppingcalculator

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.firebaseDB.FirebaseDB
import com.example.shoppingcalculator.viewmodels.DebtViewModel
import com.example.shoppingcalculator.viewmodels.EventViewModel
import com.example.shoppingcalculator.viewmodels.ExpensesViewModel
import com.vk.api.sdk.VK
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

 class EventActivity: AppCompatActivity() {

    private lateinit var rvExpenses: RecyclerView
     private lateinit var currDebt: TextView
     private var firebaseDB = FirebaseDB()
     private lateinit var currEvent: String
     private lateinit var currCode: String
     private var values = ArrayList<Expense>()
     private lateinit var adapter: EventRecyclerAdapter
     private lateinit var viewModel: ExpensesViewModel
     private lateinit var debtViewModel: DebtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        rvExpenses = findViewById(R.id.rv_expenses)
        currDebt = findViewById(R.id.tv_total_debt)

        currEvent = intent.getStringExtra("currEvent")
        currCode = intent.getStringExtra("currCode")

        //var values = ArrayList<Expense>()
        //values.add(Expense("product", "description", false, 123, Date(System.currentTimeMillis()).toString(),123.0, ArrayList()))

        viewModel = ViewModelProviders.of(this).get(ExpensesViewModel::class.java)

        debtViewModel = ViewModelProviders.of(this).get(DebtViewModel::class.java)
        debtViewModel.getUsers().observe(this, androidx.lifecycle.Observer {
            var total = 0.0
            for (item in it) {
                total += item.payment
            }
            currDebt.text = "Общая сумма: $total руб."
        })
        debtViewModel.updatePaymentUsers(currEvent)

        firebaseDB.getExpenses(currEvent) {
            firebaseDB.listenExpenseChange(currEvent, it) {
                viewModel.listenChange(currEvent)
            }
        }

        adapter = EventRecyclerAdapter(values, object: EventRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, ExpensesActivity::class.java)
                intent.putExtra("currExpense", adapter.values[position])
                intent.putExtra("currEvent", currEvent)
                startActivity(intent)
            }

            override fun onCheckBoxClick(position: Int, isChecked: Boolean) {
                firebaseDB.changeExpenseIsBought(currEvent, values[position].name, isChecked)
            }
        })
        rvExpenses.adapter = adapter
        rvExpenses.layoutManager = LinearLayoutManager(this)

        val progressBar: ProgressBar = findViewById(R.id.progressBarEvents)
        progressBar.visibility = View.VISIBLE

        viewModel.getExpenses().observe(this, androidx.lifecycle.Observer {
            adapter.values = it as ArrayList<Expense>
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        })
        viewModel.updateExpenses(currEvent)
        progressBar.visibility = View.GONE

    }

    fun onTotalDebtClick(view: View) {
        val intent = Intent(applicationContext, DebtActivity::class.java)
        intent.putExtra("currEvent", currEvent)

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
                    val progressBar: ProgressBar = findViewById(R.id.progressBarEvents)
                    progressBar.visibility = View.VISIBLE
                    firebaseDB.createExpense(currEvent, name.toString(), maybeDouble)
                    dialog.dismiss()

                    viewModel.updateExpenses(currEvent)
                    /*firebaseDB.getExpenses(currEvent) {
                        values.clear()
                        values.addAll(it)
                        adapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    }*/
                }
            }
        }
    }

     fun onShowCodeClick(view: View) {
         val dialog = AlertDialog.Builder(this)
             .setTitle("Код события")
             .setMessage("Код: $currCode")
             .setNeutralButton("Ок", null)
             .show()
     }
 }