package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import java.util.*

class HomeViewModel : ViewModel() {

    private val _availableCourses = mutableListOf<Course>()

    var availableCourses: MutableList<Course> = mutableListOf()
        get() = _availableCourses

    fun getCoursesAsArrayList(): ArrayList<Course> {
        return _availableCourses as ArrayList<Course>
    }

    init {
        _availableCourses.add(
            Course(
                "Course_0",
                "Programmazione Mobile",
                "Questo è il corso di programmazione mobile",
                Date(2021, 9, 11, 14, 45),
                Teacher(
                    "Teacher_0",
                    "Mario",
                    "",
                    "Rossi",
                ),
                "14: 45"
            )
        )

        _availableCourses.add(
            Course(
                "Course_1",
                "Tecnologie Web",
                "Questo è il corso di Tecnologie Web",
                Date(2021, 9, 12, 9, 15),
                Teacher(
                    "Teacher_1",
                    "Luigi",
                    "",
                    "Bianchi",
                ),
                "14: 00"
            ),
        )

        _availableCourses.add(
            Course(
                "Course_2",
                "Inglese",
                "Questo è il corso di Inglese",
                Date(2021, 10, 11, 17, 30),
                Teacher(
                    "Teacher_2",
                    "Pippo",
                    "",
                    "Verdi",
                ),
                "17: 00"
            )
        )
    }

}