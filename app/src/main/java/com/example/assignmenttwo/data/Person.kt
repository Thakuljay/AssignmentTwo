package com.example.assignmenttwo.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    var id: Long = 0,
    var firstname: String = "",
    var lastname: String = "",
    var imageResId: Int = 0,
    var score: Int = 0,
    var buddyId: Long? = null
) : Parcelable {

    constructor(id: Long, firstname: String, lastname: String, imageResId: Int) : this(
        id,
        firstname,
        lastname,
        imageResId,
        0,
        null
    )

    override fun toString(): String {
        return "$firstname $lastname"
    }
}
//): Comparable<Person> {
//
//    override fun compareTo(other: Person) = comparator.compare(this, other)
//
//    companion object {
//        // using the method reference syntax as an alternative to lambdas
//        val comparator = compareBy(Person::firstname, Person::score)
//    }
//}
