package com.example.shoppingcalculator

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        values.add(Event("title1",list, currentDate))

        val adapter = MainRecyclerAdapter(values, object: MainRecyclerAdapter.OnClickListener {
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
        placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "name"
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add new event")
            .setView(placeFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Apply", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //TODO добавление нового события
            dialog.dismiss()
        }
    }

    fun onJoinEventClick(view: View) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.event_dialog_layout, null)
        placeFormView.findViewById<EditText>(R.id.et_EventKey).hint = "key"
        val dialog = AlertDialog.Builder(this)
            .setTitle("Enter event key")
            .setView(placeFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Apply", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //TODO присоединение к событию
            dialog.dismiss()
        }
    }

}