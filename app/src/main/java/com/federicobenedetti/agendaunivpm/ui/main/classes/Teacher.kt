package com.federicobenedetti.agendaunivpm.ui.main.classes

open class Teacher {
    private var _logTAG = "TEACHECLASS"

    /**
     * Id del professore
     */
    private lateinit var id: String

    /**
     * Nome del professore
     */
    private lateinit var name: String

    /**
     * Secondo nome del professore
     */
    private lateinit var middleName: String

    /**
     * Cognome del professore
     */
    private lateinit var lastName: String

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