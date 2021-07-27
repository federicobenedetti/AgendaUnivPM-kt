package com.federicobenedetti.agendaunivpm.ui.main.utils

import com.google.firebase.auth.FirebaseAuth

object FirebaseUtils {
    private var mFirebaseAuth: FirebaseAuth? = null

    init {
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    public fun getFirebaseAuthInstance(): FirebaseAuth? {
        return mFirebaseAuth
    }
}