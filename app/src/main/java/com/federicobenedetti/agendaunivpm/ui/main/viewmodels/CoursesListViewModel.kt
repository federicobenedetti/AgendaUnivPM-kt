package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils

/**
 * Viewmodel relativo alla lista generale di tutti i corsi disponibili
 */
class CoursesListViewModel : ViewModel() {

    private var _availableCourses = mutableListOf<Course>()

    var availableCourses: MutableList<Course> = mutableListOf()
        get() = _availableCourses

    fun getCoursesAsArrayList(): ArrayList<Course> {
        return _availableCourses as ArrayList<Course>
    }

    init {
        var courses = DataPersistanceUtils.getCourses()
        if (courses.isEmpty()) {
            _availableCourses.clear()
        } else {
            _availableCourses = courses as MutableList<Course>
        }
    }
}