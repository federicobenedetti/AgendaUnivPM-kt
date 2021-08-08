package com.federicobenedetti.agendaunivpm.ui.main.classes

import java.util.*

open class Course {
    private var _logTAG = "COURSECLASS"

    /**
     * Id del corso
     */
    private lateinit var id: String

    /**
     * Titolo del corso
     */
    private lateinit var title: String

    /**
     * Descrizione del corso
     */
    private lateinit var description: String

    /**
     * Anno accademico del corso
     */
    private lateinit var session: Date

    /**
     * Professore che tiene il corso
     */
    private lateinit var teacher: Teacher

    constructor(
        _id: String,
        _title: String,
        _description: String,
        _session: Date,
        _teacher: Teacher
    ) {
        id = _id
        title = _title
        description = _description
        session = _session
        teacher = _teacher
    }

}