package com.example.safephone2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val name = intent.getStringExtra("ButtonText")
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val prefence = this.getSharedPreferences(packageName , Context.MODE_PRIVATE)
        val editor = prefence.edit()
        val registerButton = findViewById<Button>(R.id.buttonRegister2)
        Toast.makeText(this,name, Toast.LENGTH_SHORT).show()

        if(name.isNullOrEmpty()){
            actionBar!!.title = "Register"
            registerButton.text = "Register"

        }
        else{
            actionBar!!.title = name
                registerButton.text = name
        }
        registerButton.setOnClickListener {
            val keyText = findViewById<EditText>(R.id.pinText)
            val key = keyText.text.toString();
            editor.putString("PASSWORD", key)
            editor.apply()
            Log.d("PASSWORDD", key)
            Toast.makeText(applicationContext, "You succesfully register your pin !", Toast.LENGTH_LONG).show()

            onBackPressed()

        }

    }

}