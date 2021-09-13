package com.federicobenedetti.agendaunivpm.ui.main.singletons

import com.federicobenedetti.agendaunivpm.ui.main.classes.CalendarLesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import java.time.LocalDate

/**
 * Non avendo un DB implementato, necessito comunque di un posto in cui tenere i miei dati a runtime
 * Questa classe statica mi permette di salvarne in una sola istanza e utilizzarli ovunque mi servono
 *
 * TODO: In futuro sicuramente sarebbe da switchare a un DB locale tipo LiteDB per la persistenza dei dati
 */
object DataPersistanceUtils {
    private const val _logTAG = "DATAPERSISTANCEUTILS"

    private var teachers: List<Teacher> = listOf()

    private var courses: List<Course> = listOf()

    private var lessons: List<Lesson> = listOf()

    private var calendarLessons: List<CalendarLesson> = listOf()
    private var calendarLessonWhichUserCanSee: List<CalendarLesson> = listOf()

    fun reset() {
        teachers = emptyList()
        courses = emptyList()
        lessons = emptyList()
        calendarLessons = emptyList()
        calendarLessonWhichUserCanSee = emptyList()
    }

    fun setTeachers(t: List<Teacher>?) {
        if (!t.isNullOrEmpty()) {
            teachers = t
        }
    }

    fun getTeachers(): List<Teacher> {
        return teachers
    }

    fun getTeacherById(id: String?): Teacher? {
        Logger.d(_logTAG, "Teacher to find", id)
        return teachers?.find { t -> t.id == id }
    }

    fun setCourses(c: List<Course>?) {
        if (!c.isNullOrEmpty()) {
            courses = c
        }
    }

    fun getCourses(): List<Course> {
        return courses
    }

    fun setLessons(l: List<Lesson>?) {
        if (!l.isNullOrEmpty()) {
            lessons = l
        }
    }

    fun getLessons(): List<Lesson> {
        return lessons
    }

    fun getStudentLessons(): List<Lesson> {
        return lessons.filter { l -> WhoAmI.getStudentCoursesStringList().contains(l.courseId) }
    }

    fun getLessonById(id: String?): Lesson? {
        return lessons?.find { t -> t.id == id }
    }


    /**
     * Questa funzione non fa altro che fare un match tra le lezioni che l'utente
     * può vedere, e la lista globale di lezioni disponibili
     * In questo modo, l'utente potrà vedere a calendario solo quelle di cui è iscritto al corso relativo
     */
    fun setCalendarLessonWhichUserCanSee() {
        var lessonThatStudentCanSee = ArrayList<CalendarLesson>()

        for (lessonId in WhoAmI.getLessonStudentCanSee()) {

            for (lesson in calendarLessons) {

                if (lesson.lessonId == lessonId) {
                    lessonThatStudentCanSee.add(lesson)
                }

            }
        }

        calendarLessonWhichUserCanSee = lessonThatStudentCanSee
    }

    fun getCalendarLessonsAtDate(date: LocalDate): List<CalendarLesson> {
        var lessons = ArrayList<CalendarLesson>()
        for (lesson in calendarLessonWhichUserCanSee) {
            if (lesson.dueDate == date.toString()) {
                lessons.add(lesson)
            }
        }

        return lessons.toList()
    }

    fun getCalendarLessonsWhichUserCanSee(): List<CalendarLesson> {
        return calendarLessonWhichUserCanSee
    }

    fun setCalendarLessons(l: List<CalendarLesson>?) {
        if (!l.isNullOrEmpty()) {
            calendarLessons = l
            setCalendarLessonWhichUserCanSee()
        }
    }

    fun getCalendarLessons(): List<CalendarLesson> {
        return calendarLessons
    }
}