package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.activities.CourseDetailActivity
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.extensions.inflate


class RecyclerAdapter(private val courses: ArrayList<Course>) :
    RecyclerView.Adapter<RecyclerAdapter.InfoCardHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoCardHolder {
        val inflatedView = parent.inflate(R.layout.layout_card_info, false)
        return InfoCardHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: InfoCardHolder, position: Int) {
        val course = courses[position]
        holder.bindCourse(course)
    }

    override fun getItemCount(): Int = courses.size

    class InfoCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

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

        init {
            itemView.setOnClickListener(this)
            launchCourseDetailActivityBtn.setOnClickListener(this)
        }

        fun bindCourse(_course: Course) {
            course = _course

            textViewCourseTitle.text = course.title
            textViewCourseDescription.text = course.shortDescription
            textViewCourseTeacher.text = course.teacher.name + " " + course.teacher.lastName
            textViewCourseSession.text = course.session.year.toString()
        }

        override fun onClick(v: View?) {
            launchCourseDetailActivity()
        }

        fun launchCourseDetailActivity() {
            var intent = Intent(itemViewContext, CourseDetailActivity::class.java)

            Log.d(_logTAG, "Selected course ID: " + course.id)
            intent.putExtra("CourseId", course.id)

            itemViewContext.startActivity(intent)
        }

        companion object {
            // Add a key for easy reference to the item launching the RecyclerView.
            private const val _logTAG = "COURSE"
        }

    }

}