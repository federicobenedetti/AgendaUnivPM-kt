package com.federicobenedetti.agendaunivpm.ui.main.classes

import java.util.*

open class CalendarCourse : Course() {
    private var _logTAG = "CALENDARCOURSECLASS"

    /**
     * Per 'Due Date' si intende la data di avvenimento del corso
     * Ad esempio: Alle 14:45 del 10/09/2021
     */
    private lateinit var dueDate: Date
}