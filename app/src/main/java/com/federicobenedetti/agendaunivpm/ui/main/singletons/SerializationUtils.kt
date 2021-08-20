package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.google.gson.Gson
import com.google.gson.JsonElement

object SerializationUtils {

    /**
     * Reified: Passiamo il tipo di ritorno come 'argomento' della funzione
     * Ergo stiamo traducendo qualcosa di astratto in qualcosa di concreto (il tipo
     * viene utilizzato per la deserializzazione)
     *
     * La funzione non fa altro che serializzare la mappa per poi deserializzarla
     * nel tipo di oggetto che noi richiediamo.
     */
    inline fun <reified T> deserializeMapToT(map: Map<*, *>): T {
        val gson = Gson()
        val jsonElement: JsonElement = gson.toJsonTree(map)
        return gson.fromJson(jsonElement, T::class.java)
    }
}