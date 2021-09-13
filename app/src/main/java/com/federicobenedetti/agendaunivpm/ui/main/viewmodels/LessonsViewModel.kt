package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils
import java.util.*

/**
 * Viewmodel relativo al fragment della lista delle lezioni
 */
class LessonsViewModel : ViewModel() {

    private var _availableLessons = mutableListOf<Lesson>()

    var availableLessons: MutableList<Lesson> = mutableListOf()
        get() = _availableLessons

    fun getLessonsAsArrayList(): ArrayList<Lesson> {
        return _availableLessons as ArrayList<Lesson>
    }

    init {
        var lessons = DataPersistanceUtils.getStudentLessons()
        if (lessons.isEmpty()) {
            _availableLessons.clear()
        } else {
            _availableLessons = lessons as MutableList<Lesson>
        }
    }
}
