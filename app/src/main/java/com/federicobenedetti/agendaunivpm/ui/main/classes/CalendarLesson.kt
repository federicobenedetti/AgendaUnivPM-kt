package com.federicobenedetti.agendaunivpm.ui.main.classes

/**
 * Questa classe descrive una lezione
 * L'id permette di relazionarla alla lezione vera e propria (e quindi il dettaglio)
 * La due date permette di visualizzarla in calendario
 */
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

    /**
     * Id della lezione
     * Serve per poterla collegare al dettaglio della lezione, cliccando nel calendario
     */
    lateinit var lessonId: String

    /**
     * Per 'Due Date' si intende la data di avvenimento del corso
     * Ad esempio: 10/09/2021
     */
    lateinit var dueDate: String
}