package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.HttpsCallableResult
import com.google.gson.Gson

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

    fun getCoursesForStudent(corsi: Array<String>): Task<HttpsCallableResult> {
        val data = hashMapOf(
            "corsi" to Gson().toJson(corsi)
        )
        
        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getCoursesFromCoursesId")
            .call(data)
    }

}