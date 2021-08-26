package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.activities.CourseDetailActivity
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import com.federicobenedetti.agendaunivpm.ui.main.extensions.inflate
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils


class CourseRecyclerAdapter(
    private val courses: ArrayList<Course>
) :
    RecyclerView.Adapter<CourseRecyclerAdapter.CourseCardViewHolder>() {

    private var _logTAG = "COURSERECYCLERADAPTER"

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseCardViewHolder {
        val inflatedView = parent.inflate(R.layout.layout_card_course, false)
        return CourseCardViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CourseCardViewHolder, position: Int) {
        val course = courses[position]
        holder.bindCourse(course)
    }

    override fun getItemCount(): Int = courses.size

    class CourseCardViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var _logTAG = "CARDVIEWHOLDER"

        private val textViewCourseTitle: TextView = itemView.findViewById(R.id.textViewCourseTitle)
        private val textViewCourseDescription: TextView =
            itemView.findViewById(R.id.textViewCourseDescription)
        private val textViewCourseTeacher: TextView =
            itemView.findViewById(R.id.textViewCourseTeacher)
        private val textViewCourseSession: TextView =
            itemView.findViewById(R.id.textViewCourseSession)

        private val launchCourseDetailActivityBtn: Button =
            itemView.findViewById(R.id.btn_go_to_course_detail)

        private var itemViewContext: Context = itemView.context

        /**
         * Mi aspetto che il Corso NON sia null, in quanto se fosse null
         * non dovrebbe comparire nella lista.
         */
        private lateinit var course: Course

        private lateinit var teacher: Teacher

        init {
            itemView.setOnClickListener(this)
            launchCourseDetailActivityBtn.setOnClickListener(this)
        }

        fun bindCourse(_course: Course) {
            course = _course

            teacher = DataPersistanceUtils.getTeacherById(course.teacherId)!!

            textViewCourseTitle.text = course.title
            textViewCourseDescription.text = course.shortDescription
            textViewCourseTeacher.text = teacher.name + " " + teacher.lastName
            textViewCourseSession.text = course.session
        }

        override fun onClick(v: View?) {
            ActivityUtils.launchActivityWithParams(
                itemViewContext,
                CourseDetailActivity::class,
                hashMapOf(
                    "CourseId" to course.id
                )
            )
        }
    }
}