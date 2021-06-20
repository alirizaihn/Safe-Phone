package com.example.safephone2.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Process
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.safephone2.GlobalData
import com.example.safephone2.LockedApp
import com.example.safephone2.LoginActivity
import com.example.safephone2.database.AppDatabase
import com.example.safephone2.database.ProfileModel
import java.text.SimpleDateFormat
import java.util.*


class Myservice : Service() {
    private var sCurrentProcessName: String? = null
    private val sGetCurrentProcessNameLock = Any()
    val simpleDateFormat = SimpleDateFormat("dd-M-yyyy HH:mm")
    val db: AppDatabase = Room.databaseBuilder(GlobalData.mContext!!, AppDatabase::class.java,"notes")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    var context: Context? = null

    override fun onCreate() {
        super.onCreate()


        context = this

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)

        var currentDateAndTime: String? = null
        val today = Date()
        currentDateAndTime = simpleDateFormat.format(today)
        Log.d("TİMEMACHED",currentDateAndTime.toString())
         var sProcessList = ArrayList<String>()
        for (i in db.notedao().getActiveNotes(true).indices) {
            Log.d("TİMEMACHED2",db.notedao().getActiveNotes(true)[i].endTime)
            sProcessList.addAll(db.notedao().getActiveNotes(true)[i].apss)
            if(currentDateAndTime == (db.notedao().getActiveNotes(true)[i].endTime)){
                val note:ProfileModel= ProfileModel( db.notedao().getActiveNotes(true)[i].title,db.notedao().getActiveNotes(true)[i].endTime,false,db.notedao().getActiveNotes(true)[i].apss, db.notedao().getActiveNotes(true)[i].noteId)
                db.notedao().updateNote(note)
            }
        }
        Log.d("UYGUKAMAMAsd",sProcessList.toString())
        if(getApplications().toString() in sProcessList){
            val intent = Intent(GlobalData.mContext, LockedApp::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        }

        return START_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }

    fun getCurrentProcessName(context: Context?): String? {
        if (context == null) return sCurrentProcessName
        synchronized(sGetCurrentProcessNameLock) {
            if (sCurrentProcessName == null) {
                val activityManager =
                    context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
                val infos =
                    activityManager.runningAppProcesses ?: return null
                for (info in infos) {
                    if (info.pid == Process.myPid()) {
                        sCurrentProcessName = info.processName
                        return sCurrentProcessName
                    }
                }
            }
        }
        return sCurrentProcessName
    }
    fun getApplications ():String? {
        val am = this
            .getSystemService(ACTIVITY_SERVICE) as ActivityManager
// get the info from the currently running task
        val taskInfo = am.getRunningTasks(1)

        val componentInfo = taskInfo[0].topActivity
        return componentInfo?.getPackageName().toString()
    }

/*
    private fun initProcessList(context: Context) {
        try {
            if (sProcessList.size > 0) {
                return
            }
            sProcessList.add(context.packageName)
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(
                context.packageName,
                PackageManager.GET_RECEIVERS or PackageManager.GET_ACTIVITIES or PackageManager.GET_PROVIDERS or PackageManager.GET_SERVICES
            )
            if (packageInfo.receivers != null) {
                for (info in packageInfo.receivers) {
                    if (!sProcessList.contains(info.processName)) {
                        sProcessList.add(info.processName)
                    }
                }
            }
            if (packageInfo.providers != null) {
                for (info in packageInfo.providers) {
                    if (!sProcessList.contains(info.processName) && info.processName != null && info.authority != null
                    ) {
                        sProcessList.add(info.processName)
                    }
                }
            }
            if (packageInfo.services != null) {
                for (info in packageInfo.services) {
                    if (!sProcessList.contains(info.processName) && info.processName != null && info.name != null
                    ) {
                        sProcessList.add(info.processName)
                    }
                }
            }
            if (packageInfo.activities != null) {
                for (info in packageInfo.activities) {
                    if (!sProcessList.contains(info.processName) && info.processName != null && info.name != null
                    ) {
                        sProcessList.add(info.processName)
                    }
                }
            }
        } catch (e: NameNotFoundException) {
        }
    }

 */
}

