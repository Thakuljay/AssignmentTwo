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
import com.example.assignmenttwo.data.Persons
import kotlinx.android.synthetic.main.item_person_answer_page.view.*

class PersonsAnswerPageAdapter : RecyclerView.Adapter<PersonsAnswerPageAdapter.CustomViewHolder>() {

    private var mData: ArrayList<Person>? = null
    var mPlayer: Person? = null
    private val dataIdMap = HashMap<Long, Int>()
    private var answerMap: HashMap<Long, Long?>? = null

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //create view

        val layoutInflater = LayoutInflater.from(parent.context)
        val list = layoutInflater.inflate(R.layout.item_person_answer_page, parent, false)

        return CustomViewHolder(list)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        if (holder.spinnerPerson.adapter == null) {
            val spinnerData = ArrayList<Person?>()
            spinnerData.add(null)
            if (mData != null) {
                spinnerData.addAll(mData!!)
            }

            PersonSpinnerAdapter(
                holder.spinnerPerson.context,
                spinnerData
            ).also { adapterAnswer ->
                adapterAnswer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                holder.spinnerPerson.adapter = adapterAnswer
            }
        }

        val info = mData?.get(position)?.also { info ->
            //            if (mPlayer == info) {
            holder.firstnamePerson.text = "Firstname : " + info.firstname
            holder.lastnamePerson.text = "Lastname : " + info.lastname
            holder.imagePerson.setImageResource(info.imageResId)

            answerMap?.get(info.id)?.let { buddyId ->
                dataIdMap[buddyId]?.let { index ->
                    if (index >= 0) {
                        holder.spinnerPerson.setSelection(index + 1)
                    } else {
                        null
                    }
                }
            }
                ?: run {
                    holder.spinnerPerson.setSelection(0)
                }
        }
//
        holder.spinnerPerson.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, spinnerPosition: Int, id: Long) {
                if (info != null) {
                    if (id >= 0) {
                        answerMap?.set(info.id, id)
                    } else {
                        answerMap?.set(info.id, null)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }


    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstnamePerson: TextView = view.tvFisrtNamePerson
        val lastnamePerson: TextView = view.tvLastNamePerson
        val imagePerson: ImageView = view.image_profile_person
        val spinnerPerson: Spinner = view.planets_spinnerPerson

    }

    fun setData(data: ArrayList<Person>, answers: HashMap<Long, Long?>) {
        mPlayer = Persons.getPlayer()

        this.mData = data
        this.answerMap = answers

        dataIdMap.clear()
        data.forEachIndexed { index, person ->
            dataIdMap[person.id] = index
        }

        notifyDataSetChanged()
    }
}


