package com.example.assignmenttwo.data

import android.annotation.TargetApi
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.example.assignmenttwo.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object Persons {
    private var personLiveData: MutableLiveData<ArrayList<Person>>? = null
    private var player: Person? = null


    fun getPerson(): MutableLiveData<ArrayList<Person>> {
        if (personLiveData == null) {
            personLiveData = MutableLiveData()
            personLiveData?.value = ArrayList(InfoPersons.Info)
        }

        return personLiveData!!
    }


    fun setBuddy(newBuddy: ArrayList<Person>) {
        InfoPersons.Info = newBuddy
    }

    fun getPlayer(): Person {
        return player!!
    }

    fun setPlayer(s: Person) {
        player = s
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun updateScore(py: Person): Int {
        var score = 0
        var indexInt: Int
        val answerMap = answersMap[py.id]
        if (answerMap != null) {
            answerMap.forEach { k, v ->
                indexInt = 0

                while (indexInt < InfoPersons.Info.size) {
                    if (k == InfoPersons.Info[indexInt].id) {
                        if (v == InfoPersons.Info[indexInt].buddyId && v != null) {
                            score++
                        }

                    }
                    indexInt++
                }
            }
            indexInt = 0

            while (indexInt < InfoPersons.Info.size) {
                if (InfoPersons.Info[indexInt] == py) {
                    InfoPersons.Info[indexInt].score = score
                }
                indexInt++
            }

            // FIXME: Use Kotlin syntax instead
            //todo Logic update score


            val c1: Comparator<Person> = Comparator { o1, o2 ->
                if (o2.score == o1.score) {
                    o1.firstname.compareTo(o2.firstname)
                } else {
                    o2.score - o1.score
                }
            }

            InfoPersons.Info.sortWith(c1)
        }

        getPerson().value = ArrayList(InfoPersons.Info)

        return score
    }

    private object InfoPersons {
        internal var Info = arrayListOf(
            Person(10, "Will", "Smith", R.mipmap.person1),
            Person(20, "John", "Sena", R.mipmap.person2),
            Person(30, "Robert", "Downny J.", R.mipmap.person3),
            Person(40, "Nobita", "Nobi", R.mipmap.person4),
            Person(50, "Josafe", "Jonathan", R.mipmap.person5),
            Person(60, "Person A", "Person A", R.mipmap.person6),
            Person(70, "Person B", "Person B", R.mipmap.person7)
        )

    }

    val answersMap = HashMap<Long, HashMap<Long, Long?>?>()
}