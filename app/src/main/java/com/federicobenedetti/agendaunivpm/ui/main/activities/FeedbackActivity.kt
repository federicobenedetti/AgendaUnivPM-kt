package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityFeedbackBinding
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.FeedbackViewModel

class FeedbackActivity : AppCompatActivity() {

    private lateinit var feedbackBinding: ActivityFeedbackBinding
    private lateinit var feedbackViewModel: FeedbackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        feedbackBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback)

        feedbackViewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        feedbackBinding.feedbackViewModel = feedbackViewModel
        feedbackBinding.lifecycleOwner = this
    }
}