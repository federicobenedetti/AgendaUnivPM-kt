package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.appcompat.app.AppCompatActivity

open class CustomAppCompatActivity constructor() : AppCompatActivity() {
    protected var _logTAG: String? = null

    constructor(
        logTag: String
    ) : this() {
        this._logTAG = logTag
    }
}