package com.federicobenedetti.agendaunivpm.ui.main.activities

import CalendarLessonsAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseCalendarBinding
import com.federicobenedetti.agendaunivpm.ui.main.classes.CalendarLesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.Logger
import com.federicobenedetti.agendaunivpm.ui.main.utils.*
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseCalendarViewModel
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


/**
 * Activity che mostra un calendario con tutte le prossime lezioni
 * Le lezioni sono cliccabili, portano direttamente al dettaglio della lezione stessa
 */
class CourseCalendarActivity : CustomAppCompatActivity("COURSECALENDAR") {

    private lateinit var courseCalendarBinding: ActivityCourseCalendarBinding
    private lateinit var courseCalendarViewModel: CourseCalendarViewModel

    private lateinit var calendarLessons: List<CalendarLesson>

    private lateinit var mCalendarView: CalendarView

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mCalendarLessonsRecyclerAdapter: CalendarLessonsAdapter
    private lateinit var mRecyclerViewCalendarLesson: RecyclerView

    private val today = LocalDate.now()

    private var selectedDate: LocalDate? = null

    private val calendarLessonAdapter = CalendarLessonsAdapter(mutableListOf())

    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup activity
        courseCalendarBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_course_calendar)
        courseCalendarViewModel =
            ViewModelProvider(this).get(CourseCalendarViewModel::class.java)
        courseCalendarBinding.courseCalendarViewModel = courseCalendarViewModel
        courseCalendarBinding.lifecycleOwner = this

        // Retrieve calendar lesson for the current student
        calendarLessons = courseCalendarViewModel.calendarLessons
        Logger.d(_logTAG, "CalendarLesson", calendarLessons)

        // Setup RecyclerView and RecyclerAdapter
        linearLayoutManager = LinearLayoutManager(this)
        mRecyclerViewCalendarLesson = courseCalendarBinding.recyclerViewLessons
        mRecyclerViewCalendarLesson.layoutManager = linearLayoutManager
        mCalendarLessonsRecyclerAdapter = calendarLessonAdapter
        mRecyclerViewCalendarLesson.adapter = mCalendarLessonsRecyclerAdapter
        mCalendarView = courseCalendarBinding.calendarView
        mRecyclerViewCalendarLesson.addItemDecoration(DividerItemDecoration(this,
            linearLayoutManager.orientation))

        courseCalendarBinding.selectedDateText.text = selectionFormatter.format(today)

        /**
         * DayView container
         * specifico per la libreria che fornisce la CalendarView
         * Serve per poter customizzare a proprio piacimento la vista per il giorno visualizzato in griglia
         */
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.

            val textView = view.findViewById<TextView>(R.id.calendarDayText)
            val dotView = view.findViewById<View>(R.id.calendarDotView)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }

        /**
         * DayView container
         * specifico per la libreria che fornisce la CalendarView
         * Serve per poter customizzare a proprio piacimento la vista per il mese visualizzato in griglia
         */
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.headerTextView)
        }


        // Setup CalendarView for days
        mCalendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val dotView = container.dotView

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.primaryTextColor)
                            textView.setBackgroundResource(R.drawable.today_bg)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.primaryTextColor)
                            textView.setBackgroundResource(R.drawable.selected_day_bg)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.secondaryTextColor)
                            textView.background = null
                            dotView.isVisible =
                                calendarLessons.any() { e -> e.dueDate == day.date.toString() }
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

        // Setup CalendarView for months
        mCalendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text =
                    "${month.yearMonth.month.name.toLowerCase().capitalize()} ${month.year}"

            }
        }

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(5)
        val lastMonth = currentMonth.plusMonths(5)
        val daysOfWeek = daysOfWeekFromLocale()


        // Apply our setup to CalendarView
        mCalendarView.setup(firstMonth, lastMonth, daysOfWeek.first())
        mCalendarView.scrollToMonth(currentMonth)

    }


    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { mCalendarView.notifyDateChanged(it) }
            mCalendarView.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun updateAdapterForDate(date: LocalDate) {
        calendarLessonAdapter.apply {
            lessons.clear()
            lessons.addAll(DataPersistanceUtils.getCalendarLessonsAtDate(date))
            notifyDataSetChanged()
        }
        courseCalendarBinding.selectedDateText.text = selectionFormatter.format(date)
    }
}

