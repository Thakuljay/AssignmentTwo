package com.example.assignmenttwo.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmenttwo.R
import com.example.assignmenttwo.activity.MainActivity
import com.example.assignmenttwo.adapter.PersonsAnswerPageAdapter
import com.example.assignmenttwo.data.Person
import com.example.assignmenttwo.data.Persons


class PersonsAnswerPageFragment : Fragment() {

    private lateinit var mRecyclerViewPersonsAnswer: RecyclerView
    private lateinit var adapterPersonsAnswer: PersonsAnswerPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_persons_answer_page, container, false)
        mRecyclerViewPersonsAnswer = root.findViewById(R.id.persondRecyclerView)
        mRecyclerViewPersonsAnswer.layoutManager = LinearLayoutManager(activity)

        adapterPersonsAnswer = PersonsAnswerPageAdapter()
        mRecyclerViewPersonsAnswer.adapter = adapterPersonsAnswer
        // Inflate the layout for this fragment

        val player =
            arguments?.getParcelable<Person>(EXTRA_PLAYER) ?: throw IllegalArgumentException("Player is required.")

        Persons.getPerson().observe(this, Observer { person ->
            var answerMap = Persons.answersMap[player.id]
            if (answerMap == null) {
                answerMap = HashMap()
                Persons.answersMap[player.id] = answerMap
            }

            adapterPersonsAnswer.setData(person, answerMap)
        })

        return root
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.apply {
            findItem(R.id.action_answer_page).isVisible = false
            findItem(R.id.action_back_to_persons).isVisible = false
            findItem(R.id.action_confirm).isVisible = false
            findItem(R.id.action_ok).isVisible = true
            findItem(R.id.action_back).isVisible = false
        }
    }

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
        R.id.action_ok -> {
            val playerScore = Persons.updateScore(Persons.getPlayer())
            fragmentManager?.popBackStack()
            adapterPersonsAnswer.notifyDataSetChanged()
            Toast.makeText(context, "Your score is $playerScore", Toast.LENGTH_SHORT).show()
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

    companion object {
        const val EXTRA_PLAYER = "player"
    }
}