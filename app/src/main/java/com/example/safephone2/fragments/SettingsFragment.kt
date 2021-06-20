package com.example.safephone2.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.example.safephone2.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class SettingsFragment : Fragment() {
    private lateinit var settingsAdapter: SettingsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        settingsAdapter = SettingsAdapter(GlobalData.mContext!!)
        var listView : ListView? = null
        listView= view.findViewById<ListView>(R.id.listview)
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(GlobalData.mContext, Register::class.java)
            intent.putExtra("ButtonText", "Şifre Güncelle")
            startActivity(intent)
        }
        listView?.adapter = settingsAdapter
        return view
    }


}