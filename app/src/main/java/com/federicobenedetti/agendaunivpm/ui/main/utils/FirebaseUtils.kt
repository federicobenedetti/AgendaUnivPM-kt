package com.federicobenedetti.agendaunivpm.ui.main.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

object FirebaseUtils {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseFunctions: FirebaseFunctions? = null

    init {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseFunctions = Firebase.functions
    }

    fun getFirebaseAuthInstance(): FirebaseAuth? {
        return mFirebaseAuth
    }

    fun getFirebaseFunctionsInstance(): FirebaseFunctions? {
        return mFirebaseFunctions
    }
}