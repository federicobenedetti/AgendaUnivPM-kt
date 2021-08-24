package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher

object DataPersistanceUtils {
    private const val _logTAG = "DATAPERSISTANCEUTILS"

    private var teachers: List<Teacher> = listOf()

    fun setTeachers(t: List<Teacher>) {
        if (t != null) {
            teachers = t
        }
    }

    fun getTeachers(): List<Teacher> {
        return teachers
    }

    fun getTeacherById(id: String): Teacher? {
        return teachers.find { t -> t.id === id }
    }
}