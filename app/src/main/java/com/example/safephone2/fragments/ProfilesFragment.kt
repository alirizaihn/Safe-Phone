package com.example.safephone2.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.safephone2.CreateProfile
import com.example.safephone2.GlobalData
import com.example.safephone2.ProfilesAdapter
import com.example.safephone2.R
import com.example.safephone2.database.AppDatabase
import com.example.safephone2.database.ProfileModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ProfilesFragment : Fragment() {
    var addButton : Button? = null
    private lateinit var userAdapter:ProfilesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profiles, container, false)
        // Inflate the layout for this fragment
        val notes:List<ProfileModel>
        val db: AppDatabase = Room.databaseBuilder(GlobalData.mContext!!, AppDatabase::class.java,"notes")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        notes=db.notedao().getAllNotes()
        userAdapter = ProfilesAdapter(notes,GlobalData.mContext!!)
        var profileRecycler : RecyclerView? = null
        profileRecycler = view.findViewById(R.id.profileRcycler)
        profileRecycler.layoutManager = LinearLayoutManager(GlobalData.mContext,RecyclerView.VERTICAL,false)
        profileRecycler.adapter = userAdapter

        addButton = view.findViewById(R.id.addButton)
        addButton?.setOnClickListener {
            var intent = Intent(activity, CreateProfile::class.java)
            startActivity(intent)
        }
        return view
    }


}