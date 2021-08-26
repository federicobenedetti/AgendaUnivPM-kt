package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils

class CourseDetailViewModel : ViewModel() {
    private var _selectedCourse: Course? = null

    var selectedCourse: Course? = null
        get() = _selectedCourse

    fun setSelectedCourseById(id: String) {
        _selectedCourse = DataPersistanceUtils.getCourses().find { c -> c.id == id }
    }

}