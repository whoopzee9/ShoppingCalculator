package com.example.shoppingcalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {
    private var etLogin: EditText? = null;
    private var etPassword: EditText? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLogin = findViewById(R.id.et_login)
        etPassword = findViewById(R.id.et_password)
    }

    fun onRegisterClick(view: View) {

    }

    fun onLoginClick(view: View) {
        startMainActivityAndClose("")
    }

    private fun startMainActivityAndClose(result: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("token", result)
        intent.putExtra("username", etLogin!!.text.toString().trim())
        startActivity(intent)
        finish()
    }
}