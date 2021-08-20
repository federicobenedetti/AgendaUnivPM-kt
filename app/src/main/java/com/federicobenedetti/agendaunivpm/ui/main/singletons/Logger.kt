package com.federicobenedetti.agendaunivpm.ui.main.singletons

import android.util.Log
import com.google.gson.Gson

object Logger {
    inline fun d(tag: String, text: String) {
        Log.d(tag, text)
    }

    inline fun d(tag: String, text: String, obj: Any) {
        Log.d(tag, text + " " + Gson().toJsonTree(obj))
    }
}