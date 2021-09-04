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

    private var studentLoggedIn: Student? = null

    private var studentCourses: List<Course> = listOf()

    private var lessonStudentCanSee: ArrayList<String> = arrayListOf()

    fun reset() {
        studentLoggedIn = null
        studentCourses = listOf()
        lessonStudentCanSee = arrayListOf()
    }

    fun getStudentCourses(): List<Course> {
        Logger.d(_logTAG, "Elementi", studentCourses)
        return studentCourses
    }

    fun setLoggedInStudent(s: Student) {
        if (s != null) {
            studentLoggedIn = s
        }
    }

    fun getStudentCoursesStringList(): List<String> {
        return studentLoggedIn!!.corsi
    }

    fun setLoggedInStudentCourses(courses: List<Course>?) {
        Logger.d(_logTAG, "Corsi: ", courses)
        if (!courses.isNullOrEmpty()) {
            studentCourses = courses
            setLessonStudentCanSee()
        }
    }

    fun setLessonStudentCanSee() {
        lessonStudentCanSee = arrayListOf()

        for (course in studentCourses) {
            for (lessonId in course.lessonIds) {
                lessonStudentCanSee.add(lessonId)
            }
        }
    }

    fun getLessonStudentCanSee(): MutableList<String> {
        return lessonStudentCanSee
    }

    fun getStudentMatricola(): String {
        Logger.d(_logTAG, "Student", studentLoggedIn)
        return studentLoggedIn!!.matricola
    }

    fun getStudentPhoneNumber(): Int {
        return studentLoggedIn!!.telefono
    }

    fun getStudentCourseYear(): Int {
        return studentLoggedIn!!.annoCorso
    }

    fun getStudentSituazioneTasse(): Boolean {
        return studentLoggedIn!!.situazioneTasse
    }

    fun checkIfStudentIsSubscribedToCourse(courseId: String): Boolean {
        Logger.d(_logTAG, "is User subscribed to course $courseId")
        Logger.d(_logTAG, "student:", studentLoggedIn)
        Logger.d(_logTAG, "indexOf", studentLoggedIn!!.corsi.indexOf(courseId))
        return studentLoggedIn!!.corsi.indexOf(courseId) != -1
    }
}