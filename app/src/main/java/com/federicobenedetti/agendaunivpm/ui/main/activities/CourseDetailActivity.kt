package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityCourseDetailBinding
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseService
import com.federicobenedetti.agendaunivpm.ui.main.singletons.Logger
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.utils.makeGone
import com.federicobenedetti.agendaunivpm.ui.main.utils.makeVisible
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CourseDetailViewModel

/**
 * Activity che mostra il dettaglio del corso scelto
 *
 * Permette di iscriverti al corso (e quindi di ricevere le lezioni relative)
 * Disiscriverti dal corso
 * E andare a vedere il calendario per le prossime lezioni relative al corso
 */
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
            mBtnSubscribeToCourse.makeGone()
            mBtnUnsubscribeFromCourse.makeVisible()
        } else {
            mBtnSubscribeToCourse.makeVisible()
            mBtnUnsubscribeFromCourse.makeGone()
        }

    }
}