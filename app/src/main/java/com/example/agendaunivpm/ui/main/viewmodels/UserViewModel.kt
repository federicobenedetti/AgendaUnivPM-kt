package com.example.agendaunivpm.ui.main.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserViewModel : ViewModel() {
    private val _loggedInUser = MutableLiveData<User>()

    var loggedInUser: LiveData<User>? = null
        get() = _loggedInUser

    init {
        _loggedInUser.value = User()
    }

}

class User {
    private var displayName: String? = ""
    private var email: String? = ""
    private var id: String? = ""
    private var photoUrl: Uri? = null

    public fun getDisplayName(): String? {
        return displayName
    }

    public fun getEmail(): String? {
        return email
    }

    public fun getId(): String? {
        return id
    }

    public fun getPhotoUrl(): Uri? {
        return photoUrl
    }

    public fun parseUserFromGoogleSignIn(googleAccount: GoogleSignInAccount) {
        displayName = googleAccount.displayName
        email = googleAccount.email
        id = googleAccount.id
        photoUrl = googleAccount.photoUrl
    }
}
