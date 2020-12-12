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

class ExpensesActivity: AppCompatActivity() {

    lateinit var name: TextView
    lateinit var cost: TextView
    lateinit var rvUsers: RecyclerView
    lateinit var firebaseDB: FirebaseDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_expenses)

        name = findViewById(R.id.tv_expense_name)
        cost = findViewById(R.id.tv_expense_cost)
        rvUsers = findViewById(R.id.rv_sharing_users)

        FirebaseApp.initializeApp(applicationContext)
        firebaseDB = FirebaseDB()

        var values = ArrayList<SharingUser>()
        values.add(SharingUser("Alex", false))

        val adapter = ExpensesRecyclerAdapter(values, object: ExpensesRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                //val intent = Intent(applicationContext, ExpensesActivity::class.java)


                //startActivity(intent)
            }

        })
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)

    }

    fun onAddSharingClick(view: View) {

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