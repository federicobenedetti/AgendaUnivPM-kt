package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseCalendarBinding
import com.federicobenedetti.agendaunivpm.ui.main.classes.CalendarLesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.Logger
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.utils.DayViewContainer
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseCalendarViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder

/**
 * Activity che mostra un calendario con tutte le prossime lezioni
 * Le lezioni sono cliccabili, portano direttamente al dettaglio della lezione stessa
 */
class CourseCalendarActivity : CustomAppCompatActivity("COURSECALENDAR") {

    private lateinit var courseCalendarBinding: ActivityCourseCalendarBinding
    private lateinit var courseCalendarViewModel: CourseCalendarViewModel

    private lateinit var calendarLessons: List<CalendarLesson>

    private lateinit var mCalendarView: com.kizitonwose.calendarview.CalendarView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        courseCalendarBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_course_calendar)
        courseCalendarViewModel =
            ViewModelProvider(this).get(CourseCalendarViewModel::class.java)
        courseCalendarBinding.courseCalendarViewModel = courseCalendarViewModel
        courseCalendarBinding.lifecycleOwner = this

        calendarLessons = courseCalendarViewModel.getCalendarLessonsAsArrayList()
        Logger.d(_logTAG, "CalendarLesson", calendarLessons)

        mCalendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
            }
        }

    }
}