package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.fragment.app.Fragment

open class CustomFragment : Fragment {
    protected var _logTAG: String? = null

    constructor(
        logTag: String
    ) {
        this._logTAG = _logTAG
    }
}