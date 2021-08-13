package com.federicobenedetti.agendaunivpm.ui.main.classes

import java.util.*

open class Course {
    private var _logTAG = "COURSECLASS"

    /**
     * Id del corso
     */
    lateinit var id: String

    /**
     * Titolo del corso
     */
    lateinit var title: String

    /**
     * Descrizione del corso
     */
    lateinit var description: String

    /**
     * Anno accademico del corso
     */
    lateinit var session: Date

    /**
     * Professore che tiene il corso
     */
    lateinit var teacher: Teacher

    /**
     * L'ora nella quale il corso verrà tenuto
     */
    lateinit var bookedHour: String

    constructor()

    constructor(
        _id: String,
        _title: String,
        _description: String,
        _session: Date,
        _teacher: Teacher,
        _bookedHour: String
    ) {
        id = _id
        title = _title
        description = _description
        session = _session
        teacher = _teacher
        bookedHour = _bookedHour
    }

}