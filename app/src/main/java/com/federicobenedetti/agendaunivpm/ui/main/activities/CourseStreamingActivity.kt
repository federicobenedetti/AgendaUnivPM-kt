package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseStreamingBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseStreamingViewModel

class CourseStreamingActivity : CustomAppCompatActivity("COURSESTREAMING") {
    private lateinit var courseStreamingBinding: ActivityCourseStreamingBinding
    private lateinit var courseStreamingViewModel: CourseStreamingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        courseStreamingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_course_streaming)
        courseStreamingViewModel =
            ViewModelProvider(this).get(CourseStreamingViewModel::class.java)
        courseStreamingBinding.courseStreamingViewModel = courseStreamingViewModel
        courseStreamingBinding.lifecycleOwner = this
    }
}