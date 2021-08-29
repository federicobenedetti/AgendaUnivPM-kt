package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.content.Context
import android.os.Process
import com.federicobenedetti.agendaunivpm.ui.main.activities.LoginActivity
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.Logger
import kotlin.system.exitProcess

/**
 * Questa classe è stata creata per evitare di distruggere la UX per l'utente
 * Se l'app crasha, viene loggato il crash e viene fatta ripartire la login activity
 * piuttosto che lasciar che l'app venga chiusa forzatamente dall'os
 */
class ExceptionHandler(context: Context) : Thread.UncaughtExceptionHandler {
    private val appContext: Context = context
    private val _logTAG: String = "EXCEPTIONHANDLER"

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        Logger.d(_logTAG, "Uncaught Exception", exception)
        ActivityUtils.launchActivity(appContext, LoginActivity::class)

        //for restarting the Activity
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }
}