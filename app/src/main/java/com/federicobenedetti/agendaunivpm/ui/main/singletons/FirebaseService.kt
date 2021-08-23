package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.google.android.gms.tasks.Task
import com.google.gson.Gson

object FirebaseService {
    private const val _logTAG = "FIREBASESERVICE"

    /**
     * Qui le funzioni vengono descritte da GET / SET
     */
    fun getStudentByUid(): Task<Student> {

        return FirebaseClient.askForStudentByUid()
            .continueWith { task ->
                var student =
                    SerializationUtils.deserializeMapToT<Student>(task.result?.data as Map<String, Any>)
                student
            }
    }

    fun setUserFeedback(matricola: String, feedback: String?): Task<String> {
        return FirebaseClient.sendUserFeedback(matricola, feedback)
            .continueWith { task ->
                val result = task.result?.data as? String
                result
            }
    }

    fun getStudentCourses(corsi: List<String>): Task<List<Course>> {
        return FirebaseClient.getCoursesForStudent(corsi)
            .continueWith { task ->
                val courses = emptyList<Course>().toMutableList()
                var result = task.result?.data as ArrayList<HashMap<String, String>>
                for (c in result) {
                    courses.add(ObjectMapper().convertValue(c))
                }

                Logger.d(_logTAG, "Objects received: " + Gson().toJsonTree(courses))
                courses
            }
    }
}