package com.federicobenedetti.agendaunivpm.ui.main.singletons

import android.util.Log
import com.google.gson.Gson

/**
 * Wrapper custom per il Logger nativo di Android
 * Purtroppo, il logger nativo NON mi permette di fare un drill-down degli oggetti nella console
 * Bensì stampa il riferimento
 *
 * Utilizzando Gson, riesco a convertire gli oggetti in JSON, e li posso stampare su console
 */
object Logger {
    inline fun d(tag: String, text: String) {
        Log.d(tag, text)
    }

    inline fun d(tag: String, text: String, obj: Any?) {
        Log.d(tag, text + " " + Gson().toJsonTree(obj))
    }
}