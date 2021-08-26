package com.federicobenedetti.agendaunivpm.ui.main.classes

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

    lateinit var matricola: String

    lateinit var uid: String

    var telefono: Int = 0

    var annoCorso: Int = 0

    lateinit var corsi: List<String>

    var situazioneTasse: Boolean = false

}