package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.HttpsCallableResult

/**
 * Client (custom) per la nostra istanza Firebase
 * L'obiettivo è quello di standardizzare il modo in cui vengono chiamate le API
 *
 * Inoltre, il suo scopo, è quello di ritornare un Task del risultato della chiamata a Firebase
 * Questo client quindi non saprà mai nulla del cosa farci del risultato, ma è compito del Service di
 * spacchettare il risultato e darlo a chi l'ha chiesto
 */
object FirebaseClient {
    private const val _logTAG = "FIREBASECLIENT"

    /**
     * Qui le funzioni vengono descritte da metodi più parlanti
     */

    fun askForStudentByUid(): Task<HttpsCallableResult> {
        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getUserFromAuthUid")
            .call()
    }

    fun sendUserFeedback(matricola: String, feedback: String?): Task<HttpsCallableResult> {
        val data = hashMapOf(
            "feedback" to feedback,
            "matricola" to matricola
        )

        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("addUserFeedback")
            .call(data)
    }

    fun getCoursesForStudent(corsi: List<String>): Task<HttpsCallableResult> {

        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getCoursesFromCoursesId")
            .call(corsi)
    }

    fun getTeachers(): Task<HttpsCallableResult> {
        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getTeachers")
            .call()
    }

    fun getCourses(): Task<HttpsCallableResult> {
        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getCourses")
            .call()
    }

    fun getLessons(): Task<HttpsCallableResult> {
        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getLessons")
            .call()
    }

    fun subscribeToCourse(id: String, matricola: String): Task<HttpsCallableResult> {
        val data = hashMapOf(
            "idCorso" to id,
            "matricola" to matricola
        )

        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("addCourseToStudent")
            .call(data)
    }

    fun unsubscribeFromCourse(id: String, matricola: String): Task<HttpsCallableResult> {
        val data = hashMapOf(
            "idCorso" to id,
            "matricola" to matricola
        )

        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("removeCourseFromStudent")
            .call(data)
    }
}