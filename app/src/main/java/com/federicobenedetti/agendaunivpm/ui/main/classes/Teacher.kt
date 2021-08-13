package com.federicobenedetti.agendaunivpm.ui.main.classes

open class Teacher {
    private var _logTAG = "TEACHERCLASS"

    /**
     * Id del professore
     */
    lateinit var id: String

    /**
     * Nome del professore
     */
    lateinit var name: String

    /**
     * Secondo nome del professore
     */
    lateinit var middleName: String

    /**
     * Cognome del professore
     */
    lateinit var lastName: String

    constructor(
        _id: String,
        _name: String,
        _middleName: String,
        _lastName: String
    ) {
        id = _id
        name = _name
        middleName = _middleName
        lastName = _lastName
    }
}