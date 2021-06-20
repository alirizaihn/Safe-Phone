package com.example.safephone2

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectAppWeb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_app_web)
        title = "Uygulama Seç"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val model = installedApps()
        val adapter = CustomAdapter(model, this)
        var rcv : RecyclerView? = null
        rcv = findViewById(R.id.rcv);
        rcv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv.adapter = adapter;

        adapter.setOnClickListener(object : CustomAdapter.ClickListener{
            override fun onClick(pos: Int, aView: View) {
                Toast.makeText(this@SelectAppWeb, "model.get(pos).name", Toast.LENGTH_LONG).show()
            }
        })
        getSystemService((ACTIVITY_SERVICE))

    }
    private fun installedApps(): List<Model> {
        val list = packageManager.getInstalledPackages(0)
        var arraylist = mutableListOf<Model>()
        for (i in list.indices) {
            val packageInfo = list[i]
            val packageName = packageInfo.applicationInfo.packageName.toString();
            if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1) {

                val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val icon: Drawable =
                    this@SelectAppWeb.getPackageManager().getApplicationIcon(packageName)
                var modal = Model(appName, packageName, icon)
                arraylist.add(modal)

            }
        }
        return arraylist
    }
}