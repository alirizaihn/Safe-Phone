package com.example.safephone2

import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.safephone2.database.AppDatabase
import com.example.safephone2.database.ProfileModel


class ProfilesAdapter(val modelList: List<ProfileModel>, val context: Context) :
    RecyclerView.Adapter<ProfilesAdapter.UserViewHolder>() {


    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var isActive:TextView
        var name:TextView
        var mMenus:TextView

        init {
            isActive = v.findViewById<TextView>(R.id.isActive)
            name = v.findViewById<TextView>(R.id.profileTitle)
            mMenus = v.findViewById(R.id.popUpWindowBtn)
            mMenus.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {

           val db: AppDatabase = Room.databaseBuilder(GlobalData.mContext!!, AppDatabase::class.java,"notes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()



            val position = modelList[adapterPosition]
            val popupMenus = PopupMenu(context,v)
            popupMenus.inflate(R.menu.profile_menu)
            val item = popupMenus.menu
            item.getItem(0).title=  if(position.isActive) "Pasif" else "Aktif"
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_popup_changeActive->{
                            Log.d("BAKLAcım", position.toString())
                        val note:ProfileModel= ProfileModel( position.title,position.endTime,!position.isActive,position.apss, position.noteId)
                        db.notedao().updateNote(note)



                        /*
                        val v = LayoutInflater.from(context).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.userName)
                        val number = v.findViewById<EditText>(R.id.userNo)
                        AlertDialog.Builder(context)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.userName = name.text.toString()
                                position.userMb = number.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(context,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()


                         */
                        true
                    }
                    R.id.action_popup_delete->{
                        /**set delete*/


                        db.notedao().deleteNote(position)


                                /*
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_baseline_home_24)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                /*db.notedao().deleteNote(position)*/
                                notifyDataSetChanged()
                                Toast.makeText(context,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()

                                dialog.show()
,

                                 */


                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.profile_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = modelList[position]
        holder.isActive.text = if (newList.isActive) "Aktif" else "Pasif"
        holder.name.text = newList.title

    }

    override fun getItemCount(): Int {
        return  modelList.size
    }
}