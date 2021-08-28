package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Viewmodel relativo all'activity per inviare un feedback
 */
class FeedbackViewModel : ViewModel() {
    val userFeedback = MutableLiveData<String>()
}