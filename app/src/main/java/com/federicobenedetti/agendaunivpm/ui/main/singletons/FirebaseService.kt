package com.federicobenedetti.agendaunivpm.ui.main.singletons

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

    fun getStudentCourses(corsi: Array<String>): Task<ArrayList<Course>> {
        return FirebaseClient.getCoursesForStudent(corsi)
            .continueWith { task ->
                Logger.d(_logTAG, "Objects received: " + Gson().toJsonTree(task.result.data))
                null
            }
    }
}