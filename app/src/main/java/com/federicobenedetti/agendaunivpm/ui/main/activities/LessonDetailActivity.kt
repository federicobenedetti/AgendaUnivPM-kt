package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityLessonDetailBinding
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.LessonDetailViewModel

class LessonDetailActivity : CustomAppCompatActivity("LESSONDETAIL") {
    private lateinit var lessonDetailBinding: ActivityLessonDetailBinding
    private lateinit var lessonDetailViewModel: LessonDetailViewModel

    private lateinit var selectedLessonId: String

    private lateinit var mBtnGoToStreaming: Button
    private lateinit var mBtnGoToCourseDetail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lessonDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_lesson_detail)
        lessonDetailViewModel =
            ViewModelProvider(this).get(LessonDetailViewModel::class.java)
        lessonDetailBinding.lessonDetailViewModel = lessonDetailViewModel
        lessonDetailBinding.lifecycleOwner = this

        selectedLessonId = intent.extras?.getString("LessonId").toString()
        lessonDetailViewModel.setSelectedLessonById(selectedLessonId)

        mBtnGoToStreaming = lessonDetailBinding.mBtnGoToStreaming
        mBtnGoToStreaming.setOnClickListener {
            ActivityUtils.launchActivityWithParams(
                this,
                LessonStreamingActivity::class,
                hashMapOf("LessonId" to selectedLessonId)
            )
        }

        mBtnGoToCourseDetail = lessonDetailBinding.mBtnGoToCourseDetail
        mBtnGoToCourseDetail.setOnClickListener {
            lessonDetailViewModel.selectedLesson.let {
                if (it != null) {
                    ActivityUtils.launchActivityWithParams(
                        this,
                        CourseDetailActivity::class,
                        hashMapOf("CourseId" to (it.courseId))
                    )
                }
            }

        }

    }
}