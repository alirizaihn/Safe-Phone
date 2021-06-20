package com.example.safephone2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    var button : Button? = null
    var buttonLogin : Button? = null
    var prefence : SharedPreferences? = null
    var passText : EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        GlobalData.mContext = applicationContext

        button = findViewById(R.id.buttonRegister)
        buttonLogin = findViewById(R.id.buttonLogin)
        passText = findViewById(R.id.pinEditText)
        button?.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        buttonLogin?.setOnClickListener {
            val passUser = prefence?.getString("PASSWORD","")
            val pass = passText?.text.toString()
            if(pass?.equals(passUser)){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Incorrect Password", Toast.LENGTH_LONG).show()
            }

        }
    }
    override fun onResume() {
        super.onResume()
        prefence = this.getSharedPreferences(packageName , Context.MODE_PRIVATE)
        var value = prefence?.getString("PASSWORD","")
        if(!(value.isNullOrEmpty())) {
            button?.visibility = View.GONE
        }
    }
}