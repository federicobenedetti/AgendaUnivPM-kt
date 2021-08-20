package com.federicobenedetti.agendaunivpm.ui.main.singletons

import android.content.Context
import com.federicobenedetti.agendaunivpm.ui.main.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

object FirebaseUtils {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseFunctions: FirebaseFunctions? = null
    private var authListenerSet: Boolean = false

    init {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseFunctions = Firebase.functions
    }

    fun setAuthStateListener(context: Context) {
        if (authListenerSet == false) {
            mFirebaseAuth!!.addAuthStateListener {
                if (mFirebaseAuth!!.currentUser == null) {
                    ActivityUtils.launchActivity(context, LoginActivity::class)
                }
            }
        }

        authListenerSet = true
    }

    fun getFirebaseAuthInstance(): FirebaseAuth? {
        return mFirebaseAuth
    }

    fun getFirebaseFunctionsInstance(): FirebaseFunctions? {
        return mFirebaseFunctions
    }
}