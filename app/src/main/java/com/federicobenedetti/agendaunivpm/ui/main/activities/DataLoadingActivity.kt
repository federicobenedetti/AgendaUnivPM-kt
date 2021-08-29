package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.widget.Toast
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.classes.*
import com.federicobenedetti.agendaunivpm.ui.main.singletons.*
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.google.android.gms.tasks.Task

/**
 * Quest'activity, per ora, diventerà uno step intermedio tra la login e la main activity
 * In futuro, si potrà estendere e far diventare un setup process
 * Del tipo: l'utente potrà interagire, inserendo manualmente i propri dati anagrafici / sensibili
 * In questo modo, togliamo la competenza alla login activity di caricare tutti i dati
 * e deleghiamo questo compito a un'activity fatta apposta che ci permette di isolare le funzionalità
 * e aggiungere lo swipe-to-refresh alla main activity
 */
class DataLoadingActivity : CustomAppCompatActivity("DATALOADING") {

    private var loadingStudentCourses: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_loading)

        loadStudentData()
    }

    /**
     * Partiamo dal caricare lo studente che sta facendo la login
     */
    private fun loadStudentData() {
        FirebaseService.getStudentByUid()
            .addOnCompleteListener { task -> handleOnCompleteListener(task) }
    }

    /**
     * Questo è un metodo quasi ricorsivo
     * Abbiamo una condizione di 'break' che è quella che si innesca
     * quando stiamo caricando tutti i corsi, a quel punto lanciamo la
     * MainActivity e chiudiamo la LoginActivity
     *
     * Per i restanti casi facciamo type-checking del risultato
     * del onCompleteListener, e in base a quello concateniamo altre chiamate
     */
    private fun handleOnCompleteListener(task: Task<*>) {
        if (task.isSuccessful) {

            when (task.result) {

                is Student -> {
                    // 1: Carichiamo lo studente
                    val student = task.result as Student

                    Logger.d(_logTAG, "Risultato chiamata getStudentByUid", student)

                    WhoAmI.setLoggedInStudent(student)

                    FirebaseService.getStudentCourses(student.corsi)
                        .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                }

                is List<*> -> {
                    Logger.d(_logTAG, "E' stata ricevuta una lista")

                    if ((task.result as List<*>).all { e -> e is Lesson }) {

                        Logger.d(_logTAG, "La lista è di Lezioni")

                        // 4: Carichiamo tutte le lezioni
                        val lessons = task.result as List<Lesson>

                        Logger.d(_logTAG, "Risultato chiamata getLessons", lessons)

                        DataPersistanceUtils.setLessons(lessons)

                        FirebaseService.getCourses()
                            .addOnCompleteListener { task -> handleOnCompleteListener(task) }

                    } else if ((task.result as List<*>).all { e -> e is Course }) {
                        Logger.d(_logTAG, "La lista è di Corsi")

                        // 2: Carichiamo i corsi dello studente
                        if (loadingStudentCourses) {
                            Logger.d(_logTAG, "Stiamo caricando i corsi dello studente")

                            val courses = task.result as List<Course>

                            Logger.d(_logTAG, "Risultato chiamata getStudentCourses", courses)

                            WhoAmI.setLoggedInStudentCourses(courses)

                            loadingStudentCourses = false
                            FirebaseService.getTeachers()
                                .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                        } else {

                            // 5: Carichiamo tutti i corsi
                            Logger.d(_logTAG, "Stiamo caricanto tutti i corsi")

                            val allCourses = task.result as List<Course>

                            Logger.d(_logTAG, "Risultato chiamata getCourses", allCourses)

                            DataPersistanceUtils.setCourses(allCourses)

                            FirebaseService.getCalendarlessons()
                                .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                        }


                    } else if ((task.result as List<*>).all { e -> e is Teacher }) {
                        Logger.d(_logTAG, "La lista è di Professori")

                        // 3: Carichiamo tutti i professori
                        val teachers = task.result as List<Teacher>

                        Logger.d(_logTAG, "Risultato chiamata getTeachers", teachers)

                        DataPersistanceUtils.setTeachers(teachers)

                        FirebaseService.getLessons()
                            .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                    } else if ((task.result as List<*>).all { e -> e is CalendarLesson }) {

                        // 6: Carichiamo tutte le lezioni del calendario
                        var calendarLessons = task.result as List<CalendarLesson>

                        Logger.d(_logTAG, "Risultato chiamata getCalendarLessons", calendarLessons)

                        DataPersistanceUtils.setCalendarLessons(calendarLessons)

                        Toast.makeText(
                            this,
                            R.string.generic_success,
                            Toast.LENGTH_LONG
                        )
                            .show()

                        loadingStudentCourses = true

                        Logger.d(_logTAG, "Finito! Lancio la MainActivity")
                        ActivityUtils.launchActivity(this, MainActivity::class)
                        finish()
                    }
                }
            }

        } else {
            Logger.d(_logTAG, "Errore durante la chiamata", task.exception)
            Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show()
        }

    }
}