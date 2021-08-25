package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityLessonDetailBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.LessonDetailViewModel

class LessonDetailActivity : CustomAppCompatActivity("LESSONDETAIL") {
    private lateinit var lessonDetailBinding: ActivityLessonDetailBinding
    private lateinit var lessonDetailViewModel: LessonDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lessonDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_lesson_detail)
        lessonDetailViewModel =
            ViewModelProvider(this).get(LessonDetailViewModel::class.java)
        lessonDetailBinding.lessonDetailViewModel = lessonDetailViewModel
        lessonDetailBinding.lifecycleOwner = this
    }
}