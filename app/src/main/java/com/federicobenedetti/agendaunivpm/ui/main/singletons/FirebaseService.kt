package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.HttpsCallableResult
import com.google.gson.Gson

/**
 * Servizio che wrappa il client (custom) Firebase
 * Serve per poter unificare la risposta alle chiamate e deserializzare
 * gli oggetti che mi ritornano da Firebase
 */
object FirebaseService {
    private const val _logTAG = "FIREBASESERVICE"

    /**
     * Qui le funzioni vengono descritte da GET / SET
     */
    fun getStudentByUid(): Task<Student> {

        return FirebaseClient.askForStudentByUid()
            .continueWith { task ->
                var student =
                    SerializationUtils.deserializeMapToT<Student>(task.result?.data as HashMap<String, Any>)
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
                val courses =
                    SerializationUtils.deserializeArrayListMapToT<Course>(task.result?.data as ArrayList<HashMap<*, *>>)
                Logger.d(_logTAG, "Objects received: ", Gson().toJsonTree(courses))
                courses
            }
    }

    fun getTeachers(): Task<List<Teacher>> {
        return FirebaseClient.getTeachers()
            .continueWith { task ->
                val teachers =
                    SerializationUtils.deserializeArrayListMapToT<Teacher>(task.result?.data as ArrayList<HashMap<*, *>>)
                Logger.d(_logTAG, "Objects received: ", Gson().toJsonTree(teachers))
                teachers
            }
    }

    fun getCourses(): Task<List<Course>> {
        return FirebaseClient.getCourses()
            .continueWith { task ->
                val courses =
                    SerializationUtils.deserializeArrayListMapToT<Course>(task.result?.data as ArrayList<HashMap<*, *>>)
                Logger.d(_logTAG, "Objects received: ", Gson().toJsonTree(courses))
                courses
            }
    }

    fun getLessons(): Task<List<Lesson>> {
        return FirebaseClient.getLessons()
            .continueWith { task ->
                val lessons =
                    SerializationUtils.deserializeArrayListMapToT<Lesson>(task.result?.data as ArrayList<HashMap<*, *>>)
                Logger.d(_logTAG, "Objects received: ", Gson().toJsonTree(lessons))
                lessons
            }
    }

    fun subToCourse(idCorso: String, matricola: String): Task<HttpsCallableResult> {
        return FirebaseClient.subscribeToCourse(idCorso, matricola)
    }

    fun unsubToCourse(idCorso: String, matricola: String): Task<HttpsCallableResult> {
        return FirebaseClient.unsubscribeFromCourse(idCorso, matricola)
    }
}