package com.example.assignmenttwo.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmenttwo.activity.MainActivity
import com.example.assignmenttwo.adapter.AnswerPageAdapter
import com.example.assignmenttwo.data.Persons
import com.example.assignmenttwo.R

class AnswerPageFragment : Fragment() {
    private lateinit var recyclerViewAnswer: RecyclerView
    private lateinit var adapterAnswer: AnswerPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = "Setting Answer"
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_answer_page, container, false)
        // Inflate the layout for this fragment
        recyclerViewAnswer = root.findViewById(R.id.answerRecyclerView)
        recyclerViewAnswer.layoutManager = LinearLayoutManager(activity)

        adapterAnswer = AnswerPageAdapter()
        recyclerViewAnswer.adapter = adapterAnswer

        Persons.getPerson().observe(this, Observer { persons ->
            adapterAnswer.setData(persons)
        })

        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.apply {
            findItem(R.id.action_answer_page).setVisible(false)
            findItem(R.id.action_back_to_persons).setVisible(false)
            findItem(R.id.action_confirm).setVisible(true)
            findItem(R.id.action_ok).setVisible(false)
            findItem(R.id.action_back).setVisible(false)
        }}

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)


    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).supportActionBar?.title = "Persons"
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_confirm -> {
            val buddy = adapterAnswer.returnData()
            Persons.setBuddy(buddy!!)
            fragmentManager?.popBackStack()
            Toast.makeText(context, "Set Buddy !!!", Toast.LENGTH_SHORT).show()
            true
        }
        android.R.id.home -> {
            (activity as MainActivity).supportActionBar?.title = "Persons"
            fragmentManager?.popBackStack()
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

            true
        }


        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}