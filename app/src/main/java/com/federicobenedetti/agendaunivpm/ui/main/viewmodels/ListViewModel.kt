package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils

class ListViewModel : ViewModel() {

    private var _availableCourses = mutableListOf<Course>()

    var availableCourses: MutableList<Course> = mutableListOf()
        get() = _availableCourses

    fun getCoursesAsArrayList(): ArrayList<Course> {
        return _availableCourses as ArrayList<Course>
    }

    init {
        _availableCourses = DataPersistanceUtils.getCourses() as MutableList<Course>
    }
}