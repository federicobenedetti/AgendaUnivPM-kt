package com.federicobenedetti.agendaunivpm.ui.main.classes

/**
 * Classe che descrive un professore
 */
open class Teacher {

    constructor() {}

    constructor(_id: String, _name: String, _middleName: String, _lastName: String) {
        this.id = _id
        this.name = _name
        this.middleName = _middleName
        this.lastName = _lastName
    }

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

}