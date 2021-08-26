package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils

class LessonDetailViewModel : ViewModel() {
    private var _selectedLesson: Lesson? = null

    var selectedLesson: Lesson? = null
        get() = _selectedLesson

    private var _teacher: Teacher? = null

    var teacher: Teacher? = null
        get() = _teacher

    fun setSelectedLessonById(id: String) {
        _selectedLesson = DataPersistanceUtils.getLessons().find { l -> l.id == id }
        selectedLesson?.let { _teacher = DataPersistanceUtils.getTeacherById(it.teacherId) }
    }
}