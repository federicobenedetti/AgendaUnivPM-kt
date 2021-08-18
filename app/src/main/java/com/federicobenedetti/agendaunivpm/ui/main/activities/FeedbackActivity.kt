package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityFeedbackBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.FeedbackViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class FeedbackActivity : CustomAppCompatActivity("FEEDBACK") {

    private lateinit var feedbackBinding: ActivityFeedbackBinding
    private lateinit var feedbackViewModel: FeedbackViewModel

    private lateinit var mBtnSendFeedback: Button

    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        feedbackBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback)
        feedbackViewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        feedbackBinding.feedbackViewModel = feedbackViewModel
        feedbackBinding.lifecycleOwner = this

        mBtnSendFeedback = findViewById(R.id.btnSendFeedback)
        mBtnSendFeedback.setOnClickListener {
            onSendFeedbackClicked()
        }

        functions = Firebase.functions
    }

    private fun sendUserFeedback(): Task<String> {
        val data = hashMapOf(
            "feedback" to feedbackViewModel.userFeedback.value
        )

        return functions
            .getHttpsCallable("addUserFeedback")
            .call(data)
            .continueWith { task ->
                val result = task.result?.data as? String
                result
            }
    }

    private fun onSendFeedbackClicked() {
        Log.w(_logTAG, "User feedback: " + feedbackViewModel.userFeedback.value)
        sendUserFeedback()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    val e = task.exception
                    if (e is FirebaseFunctionsException) {
                        val code = e.code
                        val details = e.details

                        Log.w(_logTAG, "Failure code: $code")
                        Log.w(_logTAG, "Failure details: $details")
                    }

                    Log.w(_logTAG, "addMessage:onFailure", e)
                    return@OnCompleteListener
                }

                Toast.makeText(this, R.string.feedback_sent, Toast.LENGTH_LONG).show();
                finish()
            })
    }
}