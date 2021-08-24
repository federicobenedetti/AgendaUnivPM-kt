package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityFeedbackBinding
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseService
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.FeedbackViewModel

class FeedbackActivity : CustomAppCompatActivity("FEEDBACK") {

    private lateinit var feedbackBinding: ActivityFeedbackBinding
    private lateinit var feedbackViewModel: FeedbackViewModel

    private lateinit var linearLayoutLoading: LinearLayout

    private lateinit var mBtnSendFeedback: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        feedbackBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback)
        feedbackViewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        feedbackBinding.feedbackViewModel = feedbackViewModel
        feedbackBinding.lifecycleOwner = this

        linearLayoutLoading = findViewById(R.id.linear_layout_loading)

        mBtnSendFeedback = findViewById(R.id.btnSendFeedback)
        mBtnSendFeedback.setOnClickListener {
            linearLayoutLoading.visibility = View.VISIBLE
            onSendFeedbackClicked()
        }
    }


    private fun onSendFeedbackClicked() {
        Log.w(_logTAG, "User feedback: " + feedbackViewModel.userFeedback.value)

        FirebaseService.setUserFeedback(
            WhoAmI.getStudentMatricola(),
            feedbackViewModel.userFeedback.value
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, R.string.feedback_sent, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show();
            }
            linearLayoutLoading.visibility = View.GONE
            finish()
        }
    }
}