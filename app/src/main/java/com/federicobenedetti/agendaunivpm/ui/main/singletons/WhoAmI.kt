package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.google.gson.Gson

/**
 * Singleton that rapresent which student is currently logged in.
 * This is necessary as 'matricola' is our primary key for
 * Firebase Database
 */
object WhoAmI {
    private const val _logTAG = "WHOAMI"

    private var studentLoggedIn: Student? = null

    private var studentCourses: ArrayList<Course> = arrayListOf()

    fun setLoggedInStudent(s: Student) {
        if (s != null) {
            studentLoggedIn = s

            Logger.d(_logTAG, "PIPPO" + Gson().toJsonTree(s.corsi))
            FirebaseService.getStudentCourses(s.corsi).addOnCompleteListener {
                if (it.isSuccessful) {
                    Logger.d(_logTAG, "Risultato", it.result)

                } else {
                    Logger.d(_logTAG, "Fallimento" + it.exception)
                }
            }
        }
    }

    fun getStudentMatricola(): String {
        return studentLoggedIn!!.matricola
    }
}