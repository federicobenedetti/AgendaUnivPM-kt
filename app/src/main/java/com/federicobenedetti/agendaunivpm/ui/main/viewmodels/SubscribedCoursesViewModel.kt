package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import java.util.*

/**
 * Viewmodel relativo ai corsi a cui l'utente si è iscritto
 */
class SubscribedCoursesViewModel : ViewModel() {

    private var _availableCourses = mutableListOf<Course>()

    var availableCourses: MutableList<Course> = mutableListOf()
        get() = _availableCourses

    fun getCoursesAsArrayList(): ArrayList<Course> {
        return _availableCourses as ArrayList<Course>
    }

    init {
        _availableCourses = WhoAmI.getStudentCourses() as MutableList<Course>
    }

}