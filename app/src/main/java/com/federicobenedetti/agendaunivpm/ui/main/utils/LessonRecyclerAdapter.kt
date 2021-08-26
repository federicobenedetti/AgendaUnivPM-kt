package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.activities.LessonDetailActivity
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils


class LessonRecyclerAdapter(private val courses: ArrayList<Lesson>) :
    RecyclerView.Adapter<LessonRecyclerAdapter.LessonCardViewHolder>() {

    private var _logTAG = "LESSONRECYCLERADAPTER"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LessonCardViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_card_lesson, parent, false)
        return LessonCardViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LessonCardViewHolder, position: Int) {
        val course = courses[position]
        holder.bindLesson(course)
    }

    override fun getItemCount(): Int = courses.size

    class LessonCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var _logTAG = "LESSONCARDVIEWHOLDER"

        private val textViewLessonTitle: TextView = itemView.findViewById(R.id.textViewLessonTitle)
        private val textViewLessonDescription: TextView =
            itemView.findViewById(R.id.textViewLessonDescription)
        private val textViewLessonTeacher: TextView =
            itemView.findViewById(R.id.textViewLessonTeacher)
        private val textViewLessonSession: TextView =
            itemView.findViewById(R.id.textViewLessonSession)

        private val launchLessonDetailActivityBtn: Button =
            itemView.findViewById(R.id.btn_go_to_lesson_detail)

        private var itemViewContext: Context = itemView.context

        /**
         * Mi aspetto che il Corso NON sia null, in quanto se fosse null
         * non dovrebbe comparire nella lista.
         */
        private lateinit var lesson: Lesson

        private lateinit var teacher: Teacher

        init {
            itemView.setOnClickListener(this)
            launchLessonDetailActivityBtn.setOnClickListener(this)
        }

        fun bindLesson(_lesson: Lesson) {
            lesson = _lesson

            teacher = DataPersistanceUtils.getTeacherById(lesson.teacherId)!!

            textViewLessonTitle.text = lesson.title
            textViewLessonDescription.text = lesson.shortDescription
            textViewLessonTeacher.text = teacher.name + " " + teacher.lastName
            textViewLessonSession.text = lesson.session

            // TODO: bookedHour
        }

        override fun onClick(v: View?) {
            ActivityUtils.launchActivityWithParams(
                itemViewContext,
                LessonDetailActivity::class,
                hashMapOf("LessonId" to lesson.id)
            )
        }
    }
}