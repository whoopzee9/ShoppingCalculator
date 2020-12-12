package com.example.shoppingcalculator

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.firebaseDB.FirebaseDB
import com.google.firebase.FirebaseApp
import kotlin.math.cos

class ExpensesActivity: AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var cost: TextView
    private lateinit var rvUsers: RecyclerView
    private lateinit var firebaseDB: FirebaseDB
    private lateinit var currExpense: Expense
    private lateinit var currEvent: String
    private var values = ArrayList<String>()
    private lateinit var adapter: ExpensesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_expenses)

        name = findViewById(R.id.tv_expense_name)
        cost = findViewById(R.id.tv_expense_cost)
        rvUsers = findViewById(R.id.rv_sharing_users)

        FirebaseApp.initializeApp(applicationContext)
        firebaseDB = FirebaseDB()

        currExpense = intent.getParcelableExtra("currExpense")
        currEvent = intent.getStringExtra("currEvent")

        firebaseDB.getSharingUsers(currEvent, currExpense.name) {
            values.clear()
            values.addAll(it)
            adapter.notifyDataSetChanged()
        }

        name.text = currExpense.name
        cost.text = currExpense.price.toString()
        //var values = ArrayList<SharingUser>()
        //values.add(SharingUser("Alex", false))

        adapter = ExpensesRecyclerAdapter(values, object: ExpensesRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                //val intent = Intent(applicationContext, ExpensesActivity::class.java)

                //startActivity(intent)
            }

        })
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)


    }

    fun onAddSharingClick(view: View) {
        firebaseDB.joinExpense(currEvent, currExpense.name)
        firebaseDB.getSharingUsers(currEvent, currExpense.name) {
            values.clear()
            values.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun onEditExpenseClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.expense_dialog_layout, null)

        val etCost: EditText = placeFormView.findViewById(R.id.et_expense_cost)
        val etName: EditText = placeFormView.findViewById(R.id.et_expense_name)

        etCost.setText(cost.text)
        etName.setText(name.text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Изменить расход")
            .setView(placeFormView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Изменить", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //TODO добавление нового продукта
            dialog.dismiss()
        }
    }
}