package com.federicobenedetti.agendaunivpm.ui.main.classes

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseUser

open class User {
    private var _logTAG = "USERCLASS"

    /**
     * Id dell'utente
     */
    private lateinit var id: String

    /**
     * Nome (o meglio username) dell'utente
     */
    private lateinit var displayName: String

    /**
     * Email associata all'utente
     */
    private lateinit var email: String


    /**
     * URI della foto associata all'utente
     */
    private lateinit var photoUrl: Uri

    fun getDisplayName(): String? {
        return displayName
    }

    fun getEmail(): String? {
        return email
    }

    fun getId(): String? {
        return id
    }

    fun getPhotoUrl(): Uri? {
        return photoUrl
    }

    /**
     * Firebase ci ritorna un FirebaseUser
     * Noi lo dobbiamo parsare per poterlo trasformare in qualcosa che la nostra View può capire
     */
    fun parseUserFromGoogleSignIn(user: FirebaseUser?) {
        displayName = user?.displayName.toString()
        email = user?.email.toString()
        id = user?.uid.toString()
        photoUrl = user?.photoUrl!!

        Log.w(_logTAG, "Parsed user: " + displayName + ", " + email + ", " + id + ", " + photoUrl)
    }
}
