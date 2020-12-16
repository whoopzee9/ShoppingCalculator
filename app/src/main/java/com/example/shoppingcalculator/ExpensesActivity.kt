package com.example.shoppingcalculator

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.VKAPI.VKUser
import com.example.shoppingcalculator.VKAPI.VKUsersRequest
import com.example.shoppingcalculator.firebaseDB.FirebaseDB
import com.example.shoppingcalculator.viewmodels.DebtViewModel
import com.example.shoppingcalculator.viewmodels.ExpensesViewModel
import com.example.shoppingcalculator.viewmodels.SharingViewModel
import com.google.firebase.FirebaseApp
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import java.lang.NumberFormatException
import kotlin.math.cos

class ExpensesActivity: AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var cost: TextView
    private lateinit var buyer: TextView
    private lateinit var rvUsers: RecyclerView
    private lateinit var firebaseDB: FirebaseDB
    private lateinit var currExpense: Expense
    private lateinit var currEvent: String
    private var values = ArrayList<String>()
    private lateinit var adapter: ExpensesRecyclerAdapter
    private lateinit var viewModel: SharingViewModel
    private lateinit var debtViewModel: DebtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_expenses)

        name = findViewById(R.id.tv_expense_name)
        cost = findViewById(R.id.tv_expense_cost)
        buyer = findViewById(R.id.tv_expense_buyer)

        rvUsers = findViewById(R.id.rv_sharing_users)

        FirebaseApp.initializeApp(applicationContext)
        firebaseDB = FirebaseDB()

        currExpense = intent.getParcelableExtra("currExpense")
        currEvent = intent.getStringExtra("currEvent")

        name.text = currExpense.name
        cost.text = "Стоимость: " + currExpense.price.toString() + " руб."

        val array = intArrayOf(currExpense.buyer)
        VK.execute(VKUsersRequest(array), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                var users: String = ""
                for (user in result) {
                    users += user.firstName + " " + user.lastName
                }
                buyer.text = "Покупатель: " + users
            }
            override fun fail(error: Exception) {
            }
        })
        //var values = ArrayList<SharingUser>()
        //values.add(SharingUser("Alex", false))

        viewModel = ViewModelProviders.of(this).get(SharingViewModel::class.java)

        debtViewModel = ViewModelProviders.of(this).get(DebtViewModel::class.java)

        firebaseDB.getSharingUsers(currEvent, currExpense.name) {
            firebaseDB.listenSharingUsersChange(currEvent, currExpense.name, it) {
                viewModel.listenChange(currEvent, currExpense.name)
            }
        }

        adapter = ExpensesRecyclerAdapter(values, object: ExpensesRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                //val intent = Intent(applicationContext, ExpensesActivity::class.java)

                //startActivity(intent)
            }

        })
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)

        viewModel.getSharingUsers().observe(this, Observer {
            adapter.values = it as ArrayList<String>
            adapter.notifyDataSetChanged()
            val progressBar: ProgressBar = findViewById(R.id.progressBarExpenses)
            progressBar.visibility = View.GONE
        })
        viewModel.updateUsers(currEvent, currExpense.name)
        debtViewModel.updatePaymentUsers(currEvent)
    }

    fun onAddSharingClick(view: View) {
        val progressBar: ProgressBar = findViewById(R.id.progressBarExpenses)
        progressBar.visibility = View.VISIBLE
        firebaseDB.joinExpense(currEvent, currExpense.name)

        viewModel.updateUsers(currEvent, currExpense.name)
        debtViewModel.updatePaymentUsers(currEvent)
        /*firebaseDB.getSharingUsers(currEvent, currExpense.name) {
            values.clear()
            values.addAll(it)
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        }*/
    }

    fun onRemoveSharingClick(view: View) {
        val progressBar: ProgressBar = findViewById(R.id.progressBarExpenses)
        progressBar.visibility = View.VISIBLE
        val dialog = AlertDialog.Builder(this)
            .setTitle("Выход")
            .setMessage("Вы действительно хотите прекратить принимать участие?")
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Выход", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            firebaseDB.exitExpense(currEvent, currExpense.name)

            viewModel.updateUsers(currEvent, currExpense.name)
            debtViewModel.updatePaymentUsers(currEvent)
            dialog.dismiss()
        }
    }

    fun onEditExpenseClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.expense_dialog_layout, null)

        val etCost: EditText = placeFormView.findViewById(R.id.et_expense_cost)
        val etName: EditText = placeFormView.findViewById(R.id.et_expense_name)

        //etCost.setText(cost.text)
        etName.setText(name.text)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Изменить расход")
            .setView(placeFormView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Изменить", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            var price: Double
            try {
                price = etCost.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                val dialog2 = AlertDialog.Builder(this)
                    .setTitle("Ошибка!")
                    .setMessage("Неправильная стоимость!")
                    .setNeutralButton("Ок", null)
                    .show()
                dialog.dismiss()
                return@setOnClickListener
            }
            firebaseDB.changeExpensePrice(currEvent, currExpense.name, price)
            cost.text = "Стоимость: " + price.toString() + " руб."
            dialog.dismiss()
        }
    }

    fun onDeleteShareClick(view: View) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Удаление")
            .setMessage("Вы действительно хотите удалить данный продукт?")
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Удалить", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            firebaseDB.deleteExpense(currEvent, currExpense.name)
            debtViewModel.updatePaymentUsers(currEvent)

            //viewModel.updateUsers(currEvent, currExpense.name)
            finish()


            dialog.dismiss()
        }
    }

}