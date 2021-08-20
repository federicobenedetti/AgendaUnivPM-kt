package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.appcompat.app.AppCompatActivity

open class CustomAppCompatActivity constructor() : AppCompatActivity() {
    protected lateinit var _logTAG: String

    constructor(
        logTag: String
    ) : this() {
        this._logTAG = logTag
    }
}