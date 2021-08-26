package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student

/**
 * Singleton that rapresent which student is currently logged in.
 * This is necessary as 'matricola' is our primary key for
 * Firebase Database
 */
object WhoAmI {
    private const val _logTAG = "WHOAMI"

    private lateinit var studentLoggedIn: Student

    private var studentCourses: List<Course> = listOf()

    fun getStudentCourses(): List<Course> {
        Logger.d(_logTAG, "Elementi", studentCourses)
        return studentCourses
    }

    fun setLoggedInStudent(s: Student) {
        if (s != null) {
            studentLoggedIn = s
        }
    }

    fun setLoggedInStudentCourses(courses: List<Course>) {
        Logger.d(_logTAG, "Corsi: ", courses)
        if (courses != null) {
            studentCourses = courses
        }
    }

    fun getStudentMatricola(): String {
        Logger.d(_logTAG, "Student", studentLoggedIn)
        return studentLoggedIn.matricola
    }

    fun getStudentPhoneNumber(): Int {
        return studentLoggedIn.telefono
    }

    fun getStudentCourseYear(): Int {
        return studentLoggedIn.annoCorso
    }

    fun getStudentSituazioneTasse(): Boolean {
        return studentLoggedIn.situazioneTasse
    }

    fun checkIfStudentIsSubscribedToCourse(courseId: String): Boolean {
        Logger.d(_logTAG, "is User subscribed to course $courseId")
        Logger.d(_logTAG, "student:", studentLoggedIn)
        Logger.d(_logTAG, "indexOf", studentLoggedIn.corsi.indexOf(courseId))
        return studentLoggedIn.corsi.indexOf(courseId) != -1
    }
}