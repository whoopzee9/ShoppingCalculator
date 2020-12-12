package com.example.shoppingcalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils

class LoginActivity: AppCompatActivity() {
    private var etLogin: EditText? = null;
    private var etPassword: EditText? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLogin = findViewById(R.id.et_login)
        etPassword = findViewById(R.id.et_password)

        var fing = VKUtils.getCertificateFingerprint(this, this.packageName)
        if (fing != null) {
            for (item in fing) {
                println(item)
            }
        }
        if (VK.isLoggedIn()) {
            println(VK.getUserId())
            val intent = Intent(applicationContext, MainActivity::class.java)

            //intent.putExtra("token", token.accessToken)
            intent.putExtra("username", etLogin!!.text.toString().trim())
            startActivity(intent)
            finish()
        }
    }

    fun onRegisterClick(view: View) {

    }

    fun onLoginClick(view: View) {
        startMainActivityAndClose("")
    }

    fun onLoginViaVKClick(view: View) {
        VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                val intent = Intent(applicationContext, MainActivity::class.java)

                intent.putExtra("token", token.accessToken)
                intent.putExtra("username", etLogin!!.text.toString().trim())
                startActivity(intent)
                finish()
            }

            override fun onLoginFailed(errorCode: Int) {
                val toast = Toast.makeText(
                    applicationContext,
                    "Auth failed!", Toast.LENGTH_LONG
                )
                toast.show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startMainActivityAndClose(result: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("token", result)
        intent.putExtra("username", etLogin!!.text.toString().trim())
        startActivity(intent)
        finish()
    }

}