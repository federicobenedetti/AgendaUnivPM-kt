package com.federicobenedetti.agendaunivpm.ui.main.classes

open class Student {

    private var matricola: String

    private var uid: String

    private var telefono: Int = 0

    private var annoCorso: Int = 0

    private var corsi: ArrayList<Course>

    constructor(
        student: Student
    ) {
        matricola = student.matricola
        uid = student.uid
        telefono = student.telefono
        annoCorso = student.annoCorso
        corsi = student.corsi
    }
}