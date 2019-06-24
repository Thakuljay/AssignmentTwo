package com.example.assignmenttwo.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.example.assignmenttwo.data.Person

class PersonSpinnerAdapter(context: Context, objects: ArrayList<Person?>) :
    ArrayAdapter<Person>(context, android.R.layout.simple_spinner_item, objects) {
    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: -1
    }
}