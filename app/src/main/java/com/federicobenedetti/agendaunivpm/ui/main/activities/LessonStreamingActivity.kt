package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityLessonStreamingBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.LessonStreamingViewModel

class LessonStreamingActivity : CustomAppCompatActivity("LESSONSTREAMING") {
    private lateinit var lessonStreamingBinding: ActivityLessonStreamingBinding
    private lateinit var lessonStreamingViewModel: LessonStreamingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lessonStreamingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_lesson_streaming)
        lessonStreamingViewModel =
            ViewModelProvider(this).get(LessonStreamingViewModel::class.java)
        lessonStreamingBinding.lessonStreamingViewModel = lessonStreamingViewModel
        lessonStreamingBinding.lifecycleOwner = this
    }
}