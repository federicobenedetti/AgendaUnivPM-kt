package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.ActivityLessonStreamingBinding
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.LessonStreamingViewModel

/**
 * Activity che permette di fare 'streaming' della lezione selezionata
 * TODO: Ora come ora non è uno streaming vero e proprio, bensì il file video viene prima scaricato e poi riprodotto
 * TODO: Controlli per il video a schermo
 */
class LessonStreamingActivity : CustomAppCompatActivity("LESSONSTREAMING") {
    private lateinit var lessonStreamingBinding: ActivityLessonStreamingBinding
    private lateinit var lessonStreamingViewModel: LessonStreamingViewModel

    private lateinit var mVideoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lessonStreamingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_lesson_streaming)
        lessonStreamingViewModel =
            ViewModelProvider(this).get(LessonStreamingViewModel::class.java)
        lessonStreamingBinding.lessonStreamingViewModel = lessonStreamingViewModel
        lessonStreamingBinding.lifecycleOwner = this

        mVideoView = lessonStreamingBinding.mVideoView

        mVideoView.setVideoURI(Uri.parse(FirebaseUtils.mStreamingPath))
        mVideoView.requestFocus()
        mVideoView.start()

    }
}