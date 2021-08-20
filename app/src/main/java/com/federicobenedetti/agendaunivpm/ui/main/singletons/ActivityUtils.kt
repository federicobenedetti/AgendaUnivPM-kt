package com.federicobenedetti.agendaunivpm.ui.main.singletons

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

object ActivityUtils {
    fun <T : Activity> launchActivity(context: Context, activity: KClass<T>) {
        var intent = Intent(context, activity.java)
        context.startActivity(intent)
    }

    fun <T : Activity> launchActivityWithParams(
        context: Context,
        activity: KClass<T>,
        params: HashMap<String, String>
    ) {
        var intent = Intent(context, activity.java)
        for ((key, value) in params.entries) {
            intent.putExtra(key, value)
        }
        context.startActivity(intent)
    }
}