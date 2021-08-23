package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import java.util.*

class CourseDetailViewModel : ViewModel() {

    private val _availableCourses = mutableListOf<Course>()

    var availableCourses: MutableList<Course> = mutableListOf()
        get() = _availableCourses

    fun getCoursesAsArrayList(): ArrayList<Course> {
        return _availableCourses as ArrayList<Course>
    }

    private var _selectedCourse: Course? = null

    var selectedCourse: Course? = null
        get() = _selectedCourse

    fun setSelectedCourseById(id: String) {
        _selectedCourse = _availableCourses.find { c -> c.id == id }
    }

    init {
        _availableCourses.add(
            Course(
                "Course_0",
                "Programmazione Mobile",
                "Questo è il corso di programmazione mobile",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam a finibus nibh. Ut id finibus velit. Suspendisse id felis turpis. Nulla id risus id mi posuere auctor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed pulvinar finibus tellus, quis ullamcorper eros pulvinar ut.",
                "2021",
                "Teacher_0",
                "14: 45"
            )
        )

    }
}