package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityFaqBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity

class FaqActivity : CustomAppCompatActivity("FAQ") {
    private lateinit var faqBinding: ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        faqBinding = DataBindingUtil.setContentView(this, R.layout.activity_faq)

    }
}