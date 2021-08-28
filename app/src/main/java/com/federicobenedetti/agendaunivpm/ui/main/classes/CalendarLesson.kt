package com.federicobenedetti.agendaunivpm.ui.main.classes

open class CalendarLesson {
    private var _logTAG = "CALENDARLESSON"

    constructor()

    constructor(
        _lessonId: String,
        _dueDate: String
    ) {
        lessonId = _lessonId
        dueDate = _dueDate
    }

    lateinit var lessonId: String

    /**
     * Per 'Due Date' si intende la data di avvenimento del corso
     * Ad esempio: Alle 14:45 del 10/09/2021
     */
    lateinit var dueDate: String
}