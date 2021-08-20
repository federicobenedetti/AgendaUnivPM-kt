package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.google.android.gms.tasks.Task

object FirebaseService {

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
}