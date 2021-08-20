package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseDetailBinding
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseDetailViewModel

class CourseDetailActivity : CustomAppCompatActivity("COURSEDETAIL") {
    private lateinit var courseDetailBinding: ActivityCourseDetailBinding
    private lateinit var courseDetailViewModel: CourseDetailViewModel

    private lateinit var selectedCourseDetailId: String

    private lateinit var mBtnGoToCalendar: Button
    private lateinit var mBtnGoToStreaming: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(_logTAG, "Activity CourseDetail launched")

        courseDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_course_detail)
        courseDetailViewModel =
            ViewModelProvider(this).get(CourseDetailViewModel::class.java)
        courseDetailBinding.courseDetailViewModel = courseDetailViewModel
        courseDetailBinding.lifecycleOwner = this

        selectedCourseDetailId = intent.extras?.getString("CourseId").toString()

        Log.d(_logTAG, "Selected course ID: $selectedCourseDetailId")

        courseDetailViewModel.setSelectedCourseById(selectedCourseDetailId)

        mBtnGoToCalendar = courseDetailBinding.mBtnGoToCalendar
        mBtnGoToCalendar.setOnClickListener {
            ActivityUtils.launchActivityWithParams(
                this,
                CourseCalendarActivity::class,
                hashMapOf("CourseId" to selectedCourseDetailId)
            )
        }

        mBtnGoToStreaming = courseDetailBinding.mBtnGoToStreaming
        mBtnGoToStreaming.setOnClickListener {
            ActivityUtils.launchActivityWithParams(
                this,
                CourseStreamingActivity::class,
                hashMapOf("CourseId" to selectedCourseDetailId)
            )
        }
    }
}