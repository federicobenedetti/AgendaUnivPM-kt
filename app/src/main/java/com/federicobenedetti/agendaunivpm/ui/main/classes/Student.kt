package com.federicobenedetti.agendaunivpm.ui.main.classes

open class Student(
    _matricola: String,
    _uid: String,
    _telefono: Int,
    _annoCorso: Int,
    _corsi: List<String>
) {

    var matricola: String = _matricola

    var uid: String = _uid

    var telefono: Int = _telefono

    var annoCorso: Int = _annoCorso

    var corsi = _corsi

}