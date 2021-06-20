package com.example.safephone2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.room.Room
import com.example.safephone2.database.AppDatabase
import com.example.safephone2.database.ProfileModel
import java.util.*
import kotlin.collections.ArrayList

class CreateProfile : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var backButton : Button? = null
    var createProfile : Button?=null
    var firstStep : CardView? = null
    var secondtStep : CardView? = null
    var thirdStep : CardView? = null
    var profileTitle : EditText? = null
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)
        title = "Yeni Profil Oluşturrr"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        firstStep = findViewById(R.id.FirstStep)
        secondtStep = findViewById(R.id.SecondStep)
        pickDate()
        secondtStep?.setOnClickListener{
            var intent = Intent(this, SelectAppWeb::class.java)
            startActivity(intent)
        }
        createProfile = findViewById(R.id.createProfile)
        profileTitle = findViewById(R.id.profileTitle)
        val db:AppDatabase= Room.databaseBuilder(applicationContext,AppDatabase::class.java,"notes")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        createProfile?.setOnClickListener {
            if(!profileTitle?.text!!.isEmpty()){
                val endTime = "$savedDay-$savedMonth-$savedYear"+" $savedHour:$savedMinute"
                val note:ProfileModel= ProfileModel(profileTitle?.text.toString(),endTime,true,GlobalData.appArray)
                db.notedao().insertAll(note)
                startActivity(Intent(this@CreateProfile,MainActivity::class.java))
                finish()
              /*  db.ModelDao().insertAll(note)
                val logNode:List<ProfileModel>
                logNode=db.ModelDao().getAllNotes()
                Log.d("BAKALIM",logNode.toString())


               */


                finish()

            }
            }
        }
        private fun getDateTimeCalender(){
            val cal: Calendar = Calendar.getInstance()
            day= cal.get(Calendar.DAY_OF_MONTH)
            month= cal.get(Calendar.MONTH)
            year = cal.get(Calendar.YEAR)
            hour = cal.get((Calendar.HOUR))
            minute = cal.get(Calendar.MINUTE)
        }
        private fun pickDate() {
            firstStep?.setOnClickListener {
                getDateTimeCalender()

                DatePickerDialog(this,this,year,month,day).show()
            }
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            savedDay = dayOfMonth
            savedMonth = month+1
            savedYear = year
            getDateTimeCalender()
            TimePickerDialog(this,this,hour,minute,true).show()
        }
        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            savedHour = hourOfDay
            savedMinute = minute

        }
}