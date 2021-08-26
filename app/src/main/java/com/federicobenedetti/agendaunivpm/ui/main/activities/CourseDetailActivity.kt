package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseDetailBinding
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseService
import com.federicobenedetti.agendaunivpm.ui.main.singletons.Logger
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseDetailViewModel

class CourseDetailActivity : CustomAppCompatActivity("COURSEDETAIL") {
    private lateinit var courseDetailBinding: ActivityCourseDetailBinding
    private lateinit var courseDetailViewModel: CourseDetailViewModel

    private lateinit var selectedCourseDetailId: String

    private lateinit var mBtnGoToCalendar: Button
    private lateinit var mBtnSubscribeToCourse: Button
    private lateinit var mBtnUnsubscribeFromCourse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(_logTAG, "Activity CourseDetail launched")

        courseDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_course_detail)
        courseDetailViewModel =
            ViewModelProvider(this).get(CourseDetailViewModel::class.java)
        courseDetailBinding.courseDetailViewModel = courseDetailViewModel
        courseDetailBinding.lifecycleOwner = this

        selectedCourseDetailId = intent.extras?.getString("CourseId").toString()

        Logger.d(_logTAG, "Selected course ID: $selectedCourseDetailId")

        courseDetailViewModel.setSelectedCourseById(selectedCourseDetailId)

        mBtnGoToCalendar = courseDetailBinding.mBtnGoToCalendar
        mBtnGoToCalendar.setOnClickListener {
            ActivityUtils.launchActivityWithParams(
                this,
                CourseCalendarActivity::class,
                hashMapOf("CourseId" to selectedCourseDetailId)
            )
        }

        mBtnSubscribeToCourse = courseDetailBinding.mBtnSubscribeToCourse
        mBtnSubscribeToCourse.setOnClickListener {
            FirebaseService.subToCourse(selectedCourseDetailId, WhoAmI.getStudentMatricola())
                .addOnCompleteListener {
                    Toast.makeText(
                        this,
                        R.string.generic_success,
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
        }

        mBtnUnsubscribeFromCourse = courseDetailBinding.mBtnUnsubscribeFromCourse
        mBtnUnsubscribeFromCourse.setOnClickListener {
            FirebaseService.unsubToCourse(selectedCourseDetailId, WhoAmI.getStudentMatricola())
                .addOnCompleteListener {
                    Toast.makeText(
                        this,
                        R.string.generic_success,
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
        }

        if (WhoAmI.checkIfStudentIsSubscribedToCourse(selectedCourseDetailId)) {
            mBtnSubscribeToCourse.visibility = View.GONE
            mBtnUnsubscribeFromCourse.visibility = View.VISIBLE
        } else {
            mBtnSubscribeToCourse.visibility = View.VISIBLE
            mBtnUnsubscribeFromCourse.visibility = View.GONE
        }

    }
}