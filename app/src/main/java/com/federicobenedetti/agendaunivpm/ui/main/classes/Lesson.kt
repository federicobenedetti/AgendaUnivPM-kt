package com.federicobenedetti.agendaunivpm.ui.main.classes

open class Lesson(
    _id: String,
    _bookedHour: String,
    _description: String,
    _session: String,
    _shortDescription: String,
    _teacherId: String,
    _title: String
) {

    var id: String = _id
    var bookedHour: String = _bookedHour
    var description: String = _description
    var session: String = _session
    var shortDescription: String = _shortDescription
    var teacherId: String = _teacherId
    var title: String = _title
}