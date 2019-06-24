package com.example.assignmenttwo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmenttwo.R
import com.example.assignmenttwo.data.Person
import kotlinx.android.synthetic.main.item.view.image_profile
import kotlinx.android.synthetic.main.item.view.tvFisrtName
import kotlinx.android.synthetic.main.item.view.tvLastName
import kotlinx.android.synthetic.main.item_answer_page.view.*

class AnswerPageAdapter : RecyclerView.Adapter<AnswerPageAdapter.CustomViewHolder>() {

    private var mData: ArrayList<Person>? = null
    private val dataIdMap = HashMap<Long, Int>()

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //create view

        val layoutInflater = LayoutInflater.from(parent.context)
        val list = layoutInflater.inflate(R.layout.item_answer_page, parent, false)

        return CustomViewHolder(list)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        mData?.get(position)?.let { info ->
            holder.firstname.text = "Firstname : " + info.firstname
            holder.lastname.text = "Lastname : " + info.lastname
            holder.image.setImageResource(info.imageResId)
        }

        if (holder.spinner.adapter == null) {
            val spinnerData = ArrayList<Person?>()
            spinnerData.add(null)
            if (mData != null) {
                spinnerData.addAll(mData!!)
            }

            PersonSpinnerAdapter(
                holder.spinner.context,
                spinnerData
            ).also { adapterAnswer ->

                adapterAnswer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                holder.spinner.adapter = adapterAnswer
            }
        }

        mData?.get(position)?.buddyId?.let { buddyId ->
            dataIdMap[buddyId]?.let { index ->
                if (index >= 0) {
                    holder.spinner.setSelection(index + 1)
                } else {
                    null
                }
            }
        }
            ?: run {
                holder.spinner.setSelection(0)
            }


        holder.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spinnerPosition: Int, id: Long) {
                if (spinnerPosition > 0) {
                    mData?.get(position)?.buddyId = id
                } else {
                    mData?.get(position)?.buddyId = null
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstname: TextView = view.tvFisrtName
        val lastname: TextView = view.tvLastName
        val image: ImageView = view.image_profile
        val spinner: Spinner = view.planets_spinner

    }

    fun setData(data: ArrayList<Person>) {
        this.mData = data

        dataIdMap.clear()
        data.forEachIndexed { index, person ->
            dataIdMap[person.id] = index
        }

        notifyDataSetChanged()
    }

    fun returnData(): ArrayList<Person>? {
        return mData!!
    }


}



//            mData?.get(position)?.buddyId?.let { buddyId ->
//                mData?.indexOfFirst {
//                    it.id == buddyId
//                }?.let { index ->
//                    if (index >= 0) {
//                        holder.spinner.setSelection(index + 1)
//                    } else {
//                        null
//                    }
//                }
//            } ?: run {
//                holder.spinner.setSelection(0)
//            }