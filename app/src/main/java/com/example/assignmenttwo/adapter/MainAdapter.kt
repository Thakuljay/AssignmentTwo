package com.example.assignmenttwo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmenttwo.data.Person
import com.example.assignmenttwo.data.Persons
import com.example.assignmenttwo.R
import kotlinx.android.synthetic.main.item.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    private var data: ArrayList<Person>? = null
    private var onSelectListener: OnSelectListener? = null
//    private var player : String? = null


    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //create view

        val layoutInflater = LayoutInflater.from(parent.context)
        val list = layoutInflater.inflate(R.layout.item, parent, false)


        return CustomViewHolder(list)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.layoutSelector.setOnClickListener {
            onSelectListener?.onSelected(data?.get(position)!!)
            Persons.setPlayer(data?.get(position)!!)
            Toast.makeText(
                holder.layoutSelector.context,
                data?.get(position)!!.firstname + "'s answer",
                Toast.LENGTH_SHORT
            ).show()

        }

        data?.get(position)?.let { info ->
            holder.firstname.text = "Firstname : " + info.firstname
            holder.lastname.text = "Lastname : " + info.lastname
            holder.image.setImageResource(info.imageResId)
            holder.score.text = "Score : " + info.score.toString()
        }

    }


    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstname: TextView = view.tvFisrtName
        val lastname: TextView = view.tvLastName
        val image: ImageView = view.image_profile
        val score: TextView = view.tvScore
        val layoutSelector: LinearLayout = view.layout_selector
    }

    fun setData(data: ArrayList<Person>) {
        this.data = data
        notifyDataSetChanged()

    }

    fun setOnSelectListener(listener: OnSelectListener?) {
        onSelectListener = listener
    }

    interface OnSelectListener {
        fun onSelected(s: Person)
    }
//fun setPlayer(): String? {
//    return player
//}


}


