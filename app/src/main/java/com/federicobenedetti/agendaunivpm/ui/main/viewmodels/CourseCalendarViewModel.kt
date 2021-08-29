package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import com.federicobenedetti.agendaunivpm.ui.main.classes.CalendarLesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils

/**
 * Viewmodel relativo all'activity che presenta la lista delle lezioni, relative al corso, in un calendario
 */
class CourseCalendarViewModel : ViewModel() {
    private var _calendarLessons = mutableListOf<CalendarLesson>()

    var calendarLessons: MutableList<CalendarLesson> = mutableListOf()
        get() = _calendarLessons

    init {
        var lessons = DataPersistanceUtils.getCalendarLessonsWhichUserCanSee()

        _calendarLessons = if (lessons.isNotEmpty()) {
            lessons as MutableList<CalendarLesson>
        } else {
            mutableListOf()
        }
    }
}