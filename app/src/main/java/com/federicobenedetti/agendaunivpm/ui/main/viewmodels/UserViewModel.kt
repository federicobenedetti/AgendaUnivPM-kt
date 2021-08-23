package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.User
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {
    private val _loggedInUser = MutableLiveData<User>()

    var loggedInUser: LiveData<User>? = null
        get() = _loggedInUser

    init {
        _loggedInUser.value = User()
    }

    fun setCurrentLoggedInUser(user: FirebaseUser?) {
        _loggedInUser.value?.parseUserFromGoogleSignIn(user);
    }

    fun setCurrentLoggedInUserMatricola(matricola: String) {
        _loggedInUser.value?.setMatricola(matricola)
    }

    fun setCurrentLoggedInUserPhoneNumber(phone: Int) {
        _loggedInUser.value?.setPhoneNumber(phone)
    }

    fun setCurrentLoggedInUserCourseYear(year: Int) {
        _loggedInUser.value?.setCourseYear(year)
    }
}

