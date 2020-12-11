package com.example.shoppingcalculator

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.example.shoppingcalculator.firebaseDB.FirebaseDB

class MainActivity : AppCompatActivity() {
    private lateinit var rvEvents: RecyclerView
    var values = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvEvents = findViewById(R.id.rv_events)

        var list = ArrayList<String>()
        list.add("user1")
        list.add("user2")
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        values.add(Event("title1", list, currentDate))

        val adapter = MainRecyclerAdapter(values, object : MainRecyclerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, EventActivity::class.java)

                //intent.putExtra("token", result)
                //intent.putExtra("username", etLogin!!.text.toString().trim())
                startActivity(intent)
            }

        })
        rvEvents.adapter = adapter
        rvEvents.layoutManager = LinearLayoutManager(this)

    }

    fun onAddEventClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.event_dialog_layout, null)
        placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "Название"
        val dialog = AlertDialog.Builder(this)
            .setTitle("Добавить новое событие")
            .setView(placeFormView)
            .setNegativeButton("Отмена", null)
            .setPositiveButton("Добавить", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val name = placeFormView.findViewById<EditText>(R.id.et_EventKey).text
            if (name.isBlank()) {
                placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "Введите название"
            } else {
                //val db = FirebaseDB()
                var key: String = ""
                var i = 0
                while (i < 8) {
                    var number = (0..9).random()
                    key = "$key$number"
                    i += 1
                }
                //db.createEvent(name.toString(), key)
                dialog.dismiss()
            }
        }
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
                //val db = FirebaseDB()
                //db.joinEvent(key.toString())
                dialog.dismiss()
            }
        }

    }
}