package com.example.shoppingcalculator

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcalculator.VKAPI.VKUser
import com.example.shoppingcalculator.VKAPI.VKUsersRequest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.example.shoppingcalculator.firebaseDB.FirebaseDB
import com.example.shoppingcalculator.viewmodels.EventViewModel
import com.example.shoppingcalculator.viewmodels.ExpensesViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.exceptions.VKApiExecutionException
import java.time.LocalDate
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var rvEvents: RecyclerView
    var values = ArrayList<Event>()
    var firebaseDB = FirebaseDB()
    lateinit var adapter: MainRecyclerAdapter
    lateinit var viewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvEvents = findViewById(R.id.rv_events)

        Locale.setDefault(Locale("ru"))
        val config = applicationContext.resources.configuration
        config.setLocale(Locale("ru"))


        var list = ArrayList<Int>()
        //list.add(User(ArrayList(), "user1"))
        //list.add(User(ArrayList(), "user2"))
        val sdf = SimpleDateFormat("dd.M.yyyy hh:mm")
        val currentDate = sdf.format(Date())
        println(Date(System.currentTimeMillis()).toString())

        //values.add(Event("title1","1", ArrayList(), HashMap()))

        //val array = intArrayOf(210700286, 218321196)


        VK.execute(VKUsersRequest(), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                println(result)
            }
            override fun fail(error: Exception) {
            }
        })

        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)

        firebaseDB.getEvents {
            firebaseDB.listenEventChange(it) {
                viewModel.listenChange()
            }
        }

        adapter = MainRecyclerAdapter(values, object : MainRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, EventActivity::class.java)
                intent.putExtra("currEvent", adapter.values[position].name)
                intent.putExtra("currCode", adapter.values[position].code)
                //intent.putExtra("token", result)
                //intent.putExtra("username", etLogin!!.text.toString().trim())
                startActivity(intent)
            }

        })
        rvEvents.adapter = adapter
        rvEvents.layoutManager = LinearLayoutManager(this)

        val progressBar: ProgressBar = findViewById(R.id.progressBarMain)
        progressBar.visibility = View.VISIBLE

        viewModel.getEvents().observe(this, androidx.lifecycle.Observer {
            println("OBSERVING------------------------------------------------------------------" + it.size)
            val newValues = ArrayList<Event>()
            for (item in it) {
                if (item.users.containsValue(VK.getUserId().toString())) {
                    newValues.add(item)
                }
            }
            println(newValues.size)
            adapter.values = newValues
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        })
        viewModel.updateEvents()
        /*firebaseDB.getEvents {
            for (item in it) {
                if (item.users.containsValue(VK.getUserId().toString())) {
                    values.add(item)
                }
            }
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        }*/

    }

    override fun onResume() {
        super.onResume()

        viewModel.updateEvents()
    }

    fun onAddEventClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.event_adding_layout, null)
        placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "Название"
        val ETDate = placeFormView.findViewById<EditText>(R.id.et_Date)
        val ETTime = placeFormView.findViewById<EditText>(R.id.et_Time)

        ETDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                ETDate.setText("$dayOfMonth.$monthOfYear.$year")

            }, year, month, day)

            dpd.show()
        }

        ETTime.setOnClickListener {
            val c = Calendar.getInstance()
            val time = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                ETTime.setText(getFormattedTime(hourOfDay, minute))
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
            time.show()
        }
        
        val dialog = AlertDialog.Builder(this)
            .setTitle("Добавить новое событие")
            .setView(placeFormView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Добавить", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val name = placeFormView.findViewById<EditText>(R.id.et_EventKey).text.trim()
            val dateTime: String = ETDate.text.toString() + " " + ETTime.text.toString()
            if (name.isBlank()) {
                placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "Введите название"
            } else {
                var key: String = ""
                var i = 0
                while (i < 8) {
                    var number = (0..9).random()
                    key = "$key$number"
                    i += 1
                }
                firebaseDB.createEvent(name.toString(), dateTime, key)
                dialog.dismiss()
                viewModel.updateEvents()
            }
        }
    }

    fun getFormattedTime(hour: Int, minute: Int): String {
        val hourStr: String = if (hour < 10) {
            "0$hour"
        } else {
            hour.toString()
        }
        var minuteStr: String = if (minute < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        return "$hourStr:$minuteStr"
    }

    fun onJoinEventClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.event_dialog_layout, null)
        placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "Код"
        val dialog = AlertDialog.Builder(this)
            .setTitle("Введите код события")
            .setView(placeFormView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Войти", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val key = placeFormView.findViewById<EditText>(R.id.et_EventKey).text
            if (key.isBlank()) {
                placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "Введите ключ"
            } else {
                val progressBar: ProgressBar = findViewById(R.id.progressBarMain)
                progressBar.visibility = View.VISIBLE
                firebaseDB.joinEvent(key.toString())
                dialog.dismiss()
                viewModel.updateEvents()
                /*firebaseDB.getEvents {
                    for (item in it) {
                        if (item.users.containsValue(VK.getUserId().toString())) {
                            values.add(item)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                }*/
            }
        }

    }
}