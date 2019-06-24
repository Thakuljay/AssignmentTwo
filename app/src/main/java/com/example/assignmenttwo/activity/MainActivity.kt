package com.example.assignmenttwo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmenttwo.R
import com.example.assignmenttwo.fragment.AnswerPageFragment
import com.example.assignmenttwo.fragment.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, mainFragment)
            addToBackStack(null)
            commit()
        }

        mainFragment.setOnAnswerOageListener(object : MainFragment.OnAnswerPageListener {
            override fun onAnswerPage() {
                val answerPageFragment = AnswerPageFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, answerPageFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        })

    }

}
