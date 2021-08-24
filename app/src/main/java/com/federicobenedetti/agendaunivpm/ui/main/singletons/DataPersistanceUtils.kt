package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher

object DataPersistanceUtils {
    private const val _logTAG = "DATAPERSISTANCEUTILS"

    private var teachers: List<Teacher> = listOf()

    private var courses: List<Course> = listOf()

    fun setTeachers(t: List<Teacher>) {
        if (t != null) {
            teachers = t
        }
    }

    fun getTeachers(): List<Teacher> {
        return teachers
    }

    fun getTeacherById(id: String): Teacher {
        Logger.d(_logTAG, "Teacher to find", id)
        return teachers.find { t -> t.id == id }!!
    }

    fun setCourses(c: List<Course>) {
        if (c != null) {
            courses = c
        }
    }

    fun getCourses(): List<Course> {
        return courses
    }

    fun getCourseById(id: String): Course? {
        return courses.find { t -> t.id === id }
    }
}