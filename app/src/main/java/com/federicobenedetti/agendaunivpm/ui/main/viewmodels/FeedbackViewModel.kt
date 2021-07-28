package com.federicobenedetti.agendaunivpm.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedbackViewModel : ViewModel() {
    val userFeedback = MutableLiveData<String>()
}