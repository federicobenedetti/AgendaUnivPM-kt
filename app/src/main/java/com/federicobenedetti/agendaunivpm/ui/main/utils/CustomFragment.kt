package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.fragment.app.Fragment

/**
 * Classe derivata dalla superclasse Fragment
 * Ci serve per poter dichiarare un TAG per il log
 */
open class CustomFragment : Fragment {
    protected var _logTAG: String? = null

    constructor(
        logTag: String
    ) {
        this._logTAG = logTag
    }
}