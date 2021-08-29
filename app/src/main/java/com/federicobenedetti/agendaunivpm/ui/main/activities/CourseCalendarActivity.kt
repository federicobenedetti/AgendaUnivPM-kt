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
import com.federicobenedetti.agendaunivpm.ui.main.utils.MonthViewContainer
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseCalendarViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import java.time.DayOfWeek
import java.time.YearMonth

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

        mCalendarView = courseCalendarBinding.calendarView

        mCalendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()
            }
        }

        mCalendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text =
                    "${month.yearMonth.month.name.toLowerCase().capitalize()} ${month.year}"
            }
        }

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)

        // Six row calendar for month mode
        mCalendarView.updateMonthConfiguration(
            inDateStyle = InDateStyle.FIRST_MONTH,
            maxRowCount = 6,
            hasBoundaries = true
        )

        val daysOfWeek = arrayOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
        mCalendarView.setup(firstMonth, lastMonth, daysOfWeek.first())
        mCalendarView.scrollToMonth(currentMonth)

    }
}