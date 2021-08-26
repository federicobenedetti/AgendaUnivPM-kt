package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils
import java.util.*

class LessonsViewModel : ViewModel() {

    private var _availableLessons = mutableListOf<Lesson>()

    var availableLessons: MutableList<Lesson> = mutableListOf()
        get() = _availableLessons

    fun getLessonsAsArrayList(): ArrayList<Lesson> {
        return _availableLessons as ArrayList<Lesson>
    }

    init {
        _availableLessons = DataPersistanceUtils.getLessons() as MutableList<Lesson>
    }
}
