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

        DataPersistanceUtils.reset()
        WhoAmI.reset()

        // FirebaseUtils.reset()

        loadStudentData()
    }

    /**
     * Partiamo dal caricare lo studente che sta facendo la login
     */
    private fun loadStudentData() {
        FirebaseService.getStudentByUid()
            .addOnCompleteListener { task -> handleGetStudentByUid(task) }
    }

    // Carichiamo lo studente partendo dal suo UID google
    private fun handleGetStudentByUid(task: Task<*>) {
        if (task.isSuccessful) {
            val student = task.result as Student

            Logger.d(_logTAG, "Risultato chiamata getStudentByUid", student)

            WhoAmI.setLoggedInStudent(student)

            FirebaseService.getStudentCourses(student.corsi)
                .addOnCompleteListener { task -> handleGetStudentCourses(task) }
        } else {
            handleErrorDuringRequest(task.exception)
        }
    }

    // Carichiamo i corsi dello studente
    private fun handleGetStudentCourses(task: Task<*>) {
        if (task.isSuccessful) {
            val courses = task.result as List<Course>

            Logger.d(_logTAG, "Risultato chiamata getStudentCourses", courses)

            WhoAmI.setLoggedInStudentCourses(courses)

            FirebaseService.getTeachers()
                .addOnCompleteListener { task -> handleGetTeachers(task) }
        } else {
            handleErrorDuringRequest(task.exception)
        }
    }

    // Carichiamo tutti i professori
    private fun handleGetTeachers(task: Task<*>) {
        if (task.isSuccessful) {
            // 3: Carichiamo tutti i professori
            val teachers = task.result as List<Teacher>

            Logger.d(_logTAG, "Risultato chiamata getTeachers", teachers)

            DataPersistanceUtils.setTeachers(teachers)

            FirebaseService.getLessons()
                .addOnCompleteListener { task -> handleGetLessons(task) }
        } else {
            handleErrorDuringRequest(task.exception)
        }

    }

    // Carichiamo le lezioni
    private fun handleGetLessons(task: Task<*>) {
        if (task.isSuccessful) {
            val lessons = task.result as List<Lesson>

            Logger.d(_logTAG, "Risultato chiamata getLessons", lessons)

            DataPersistanceUtils.setLessons(lessons)

            FirebaseService.getCourses()
                .addOnCompleteListener { task -> handleGetCourses(task) }
        } else {
            handleErrorDuringRequest(task.exception)
        }


    }

    // Carichiamo i corsi
    private fun handleGetCourses(task: Task<*>) {
        if (task.isSuccessful) {
            val allCourses = task.result as List<Course>

            Logger.d(_logTAG, "Risultato chiamata getCourses", allCourses)

            DataPersistanceUtils.setCourses(allCourses)

            FirebaseService.getCalendarlessons()
                .addOnCompleteListener { task -> handleGetCalendarLessons(task) }
        } else {
            handleErrorDuringRequest(task.exception)
        }

    }

    // Carichiamo il calendario poi procediamo a lanciare la main activity
    private fun handleGetCalendarLessons(task: Task<*>) {
        if (task.isSuccessful) {
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
        } else {
            handleErrorDuringRequest(task.exception)
        }

    }

    private fun handleErrorDuringRequest(exception: Exception?) {
        Logger.d(_logTAG, "Errore durante la chiamata", exception)
        Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show()

        ActivityUtils.launchActivity(this, LoginActivity::class)
    }
}