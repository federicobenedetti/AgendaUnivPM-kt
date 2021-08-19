package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Student

/**
 * Singleton that rapresent which student is currently logged in.
 * This is necessary as 'matricola' is our primary key for
 * Firebase Database
 */
object WhoAmI {
    private var studentLoggedIn: Student? = null

    fun setLoggedInStudent(s: Student) {
        studentLoggedIn = s
    }

    fun getStudentMatricola(): String {
        return studentLoggedIn!!.matricola
    }
}