package com.example.assignmenttwo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmenttwo.R
import com.example.assignmenttwo.activity.MainActivity
import com.example.assignmenttwo.adapter.MainAdapter
import com.example.assignmenttwo.data.Person
import com.example.assignmenttwo.data.Persons

class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MainAdapter
    private var onAnswerPageListener: OnAnswerPageListener? = null

    private var player: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = "Persons"
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment

        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = MainAdapter()
        recyclerView.adapter = adapter

        adapter.setOnSelectListener(object : MainAdapter.OnSelectListener {
            override fun onSelected(s: Person) {
                val personsAnswerPageFragment = PersonsAnswerPageFragment()
                personsAnswerPageFragment.arguments = Bundle().apply {
                    putParcelable(PersonsAnswerPageFragment.EXTRA_PLAYER, s)
                }

                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, personsAnswerPageFragment)
                    ?.addToBackStack(null)
                    ?.commit()
                (activity as MainActivity).supportActionBar?.title = s.firstname + "'s answer"
            }

        })

        Persons.getPerson().observe(this, Observer { person ->
            adapter.setData(person)
        })
        return root
    }


    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.apply { findItem(R.id.action_back_to_persons).setVisible(false)
            findItem(R.id.action_confirm).setVisible(false)
            findItem(R.id.action_answer_page).setVisible(true)
            findItem(R.id.action_ok).setVisible(false)
            findItem(R.id.action_back).setVisible(false)}





    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_answer_page -> {
            // User chose the "Settings" item, show the app settings UI...
            onAnswerPageListener?.onAnswerPage()
            true
        }


        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun setOnAnswerOageListener(listener: OnAnswerPageListener?) {
        onAnswerPageListener = listener
    }

    interface OnAnswerPageListener {
        fun onAnswerPage()
    }


}

