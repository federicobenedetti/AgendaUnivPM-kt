package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseCalendarBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseCalendarViewModel

class CourseCalendarActivity : CustomAppCompatActivity("COURSECALENDAR") {

    private lateinit var courseCalendarBinding: ActivityCourseCalendarBinding
    private lateinit var courseCalendarViewModel: CourseCalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        courseCalendarBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_course_calendar)
        courseCalendarViewModel =
            ViewModelProvider(this).get(CourseCalendarViewModel::class.java)
        courseCalendarBinding.courseCalendarViewModel = courseCalendarViewModel
        courseCalendarBinding.lifecycleOwner = this
    }
}