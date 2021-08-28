package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.appcompat.app.AppCompatActivity

/**
 * Classe derivata dalla superclasse AppCompatActivity
 * Ci serve per poter dichiarare un TAG per il log
 */
open class CustomAppCompatActivity constructor() : AppCompatActivity() {
    protected lateinit var _logTAG: String

    constructor(
        logTag: String
    ) : this() {
        this._logTAG = logTag
    }
}