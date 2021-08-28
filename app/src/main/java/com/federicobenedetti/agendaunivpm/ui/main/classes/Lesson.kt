package com.federicobenedetti.agendaunivpm.ui.main.classes

/**
 * Questa classe descrive la lezione e il dettaglio
 */
open class Lesson {

    constructor()

    constructor(
        _id: String,
        _bookedHour: String,
        _description: String,
        _session: String,
        _shortDescription: String,
        _teacherId: String,
        _title: String,
        _courseId: String
    ) {
        this.id = _id
        this.bookedHour = _bookedHour
        this.description = _description
        this.session = _session
        this.shortDescription = _shortDescription
        this.teacherId = _teacherId
        this.title = _title
        this.courseId = _courseId
    }

    /**
     * Id della lezione
     */
    lateinit var id: String

    /**
     * Ora d'inizio della lezione
     * Verrà mostrata nella card
     */
    lateinit var bookedHour: String

    /**
     * Descrizione (lunga) della lezione
     * Verrà mostrata nel dettaglio
     */
    lateinit var description: String

    /**
     * Anno scolastico della lezione (ad esempio 2020/2021)
     */
    lateinit var session: String

    /**
     * Descrizione (corta) della lezione
     * Verrà mostrata nella card
     */
    lateinit var shortDescription: String

    /**
     * Id del professore che terrà la lezione
     */
    lateinit var teacherId: String

    /**
     * Titolo della lezione
     */
    lateinit var title: String

    /**
     * Id del corso a cui appartiene la lezione
     */
    lateinit var courseId: String
}