package com.federicobenedetti.agendaunivpm.ui.main.classes

/**
 * Classe che descrive lo studente che sta utilizzando l'app
 */
open class Student {
    constructor()

    constructor(
        _matricola: String,
        _uid: String,
        _telefono: Int,
        _annoCorso: Int,
        _corsi: List<String>,
        _situazioneTasse: Boolean
    ) {
        this.matricola = _matricola
        this.uid = _uid
        this.telefono = _telefono
        this.annoCorso = _annoCorso
        this.corsi = _corsi
        this.situazioneTasse = _situazioneTasse
    }

    /**
     * Matricola (autogenerata da una Firebase Function) dello studente
     */
    lateinit var matricola: String

    /**
     * UID = id univoco generato da Firebase
     */
    lateinit var uid: String

    /**
     * Numero di telefono dello studente
     */
    var telefono: Int = 0

    /**
     * Anno universitario dello studente
     */
    var annoCorso: Int = 0

    /**
     * Lista dei corsi a cui è iscritto lo studente
     */
    lateinit var corsi: List<String>

    /**
     * Situazione tasse relativa allo studente: Da pagare / regolare
     */
    var situazioneTasse: Boolean = false

}