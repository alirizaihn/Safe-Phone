package com.example.safephone2
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*
import java.security.AccessController.getContext
class CustomAdapter(val modelList: List<Model>, val context: Context) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(modelList.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_item, parent, false))
    }

    override fun getItemCount(): Int {
        return modelList.size;
    }

    lateinit var mClickListener: ClickListener

    fun setOnClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(model: Model): Unit {
            itemView.itemCard.setOnClickListener {
                if(itemView.checkbox.isChecked){
                    GlobalData.appArray.remove(model.packageName)
                    itemView.checkbox.isChecked=false;}
                else{
                    GlobalData.appArray.add(model.packageName)
                    itemView.checkbox.isChecked=true;
                }
            }
            itemView.txt.text = model.name
            itemView.sub_txt.text = model.packageName
            //val id = context.resources.getIdentifier(model.name.toLowerCase(), "drawable", context.packageName)
            itemView.img.setImageDrawable(model.icon)

        }

        override fun onClick(p0: View?) {

            mClickListener.onClick(adapterPosition, itemView)
        }
    }
}