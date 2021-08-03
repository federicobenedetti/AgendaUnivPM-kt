package com.federicobenedetti.agendaunivpm.ui.main.classes

import android.net.Uri
import android.util.Log
import com.federicobenedetti.agendaunivpm.R
import com.google.firebase.auth.FirebaseUser

class User {
    private var _logTAG = "USERCLASS"

    private var displayName: String? = ""
    private var email: String? = ""
    private var id: String? = ""
    private var photoUrl: Uri? =
        Uri.parse("android.resource://com.federicobenedetti.univpm/" + R.drawable.ic_launcher_background);

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

    fun parseUserFromGoogleSignIn(user: FirebaseUser?) {
        displayName = user?.displayName
        email = user?.email
        id = user?.uid
        photoUrl = user?.photoUrl

        Log.w(_logTAG, "Parsed user: " + displayName + ", " + email + ", " + id + ", " + photoUrl)
    }
}
