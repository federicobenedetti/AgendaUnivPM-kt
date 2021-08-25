package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.google.gson.Gson
import com.google.gson.JsonElement

/**
 * Utility statica di serializzazione / deserializzazione
 *
 * Firebase torna delle HashMap, o degli array di HashMap
 * Utilizzando Jackson / Gson riesco a convertire l'HashMap nell'oggetto che mi serve
 */
object SerializationUtils {

    /**
     * Reified: Passiamo il tipo di ritorno come 'argomento' della funzione
     * Ergo stiamo traducendo qualcosa di astratto in qualcosa di concreto (il tipo
     * viene utilizzato per la deserializzazione)
     *
     * La funzione non fa altro che serializzare la mappa per poi deserializzarla
     * nel tipo di oggetto che noi richiediamo.
     */
    inline fun <reified T> deserializeMapToT(map: HashMap<*, *>): T {
        val gson = Gson()
        val jsonElement: JsonElement = gson.toJsonTree(map)
        return gson.fromJson(jsonElement, T::class.java)
    }

    inline fun <reified T> deserializeArrayListMapToT(array: ArrayList<HashMap<*, *>>): MutableList<T> {
        val elements = emptyList<T>().toMutableList()
        for (c in array) {
            elements.add(ObjectMapper().convertValue(c))
        }
        Logger.d("pippo", "p", elements)
        return elements
    }
}