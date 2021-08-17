package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseDetailBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseDetailViewModel

class CourseDetailActivity : CustomAppCompatActivity("COURSEDETAIL") {
    private lateinit var courseDetailBinding: ActivityCourseDetailBinding
    private lateinit var courseDetailViewModel: CourseDetailViewModel

    private lateinit var selectedCourseDetailId: String

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
    }
}