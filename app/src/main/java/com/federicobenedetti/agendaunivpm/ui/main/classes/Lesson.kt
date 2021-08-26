package com.federicobenedetti.agendaunivpm.ui.main.classes

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

    lateinit var id: String
    lateinit var bookedHour: String
    lateinit var description: String
    lateinit var session: String
    lateinit var shortDescription: String
    lateinit var teacherId: String
    lateinit var title: String
    lateinit var courseId: String
}