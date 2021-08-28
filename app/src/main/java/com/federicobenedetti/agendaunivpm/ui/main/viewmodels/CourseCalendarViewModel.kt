package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.CalendarLesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils
import java.util.*

class CourseCalendarViewModel : ViewModel() {
    private var _calendarLessons = mutableListOf<CalendarLesson>()

    var calendarLessons: MutableList<CalendarLesson> = mutableListOf()
        get() = _calendarLessons

    fun getCalendarLessonsAsArrayList(): ArrayList<CalendarLesson> {
        return _calendarLessons as ArrayList<CalendarLesson>
    }

    init {
        _calendarLessons = DataPersistanceUtils.getCalendarLessons() as MutableList<CalendarLesson>
    }
}