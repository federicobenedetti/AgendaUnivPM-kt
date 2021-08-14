package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.content.Intent
import android.view.View
import android.view.ViewGroup
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

    class InfoCardHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private val textViewCourseTitle: TextView = itemView.findViewById(R.id.textViewCourseTitle)
        private val textViewCourseDescription: TextView =
            itemView.findViewById(R.id.textViewCourseDescription)
        private val textViewCourseTeacher: TextView =
            itemView.findViewById(R.id.textViewCourseTeacher)
        private val textViewCourseSession: TextView =
            itemView.findViewById(R.id.textViewCourseSession)

        private var view: View = v
        private var course: Course? = null

        init {
            v.setOnClickListener(this)
        }

        fun bindCourse(course: Course) {
            textViewCourseTitle.text = course.title
            textViewCourseDescription.text = course.description
            textViewCourseTeacher.text = course.teacher.name + " " + course.teacher.lastName
            textViewCourseSession.text = course.session.year.toString()
        }

        override fun onClick(v: View?) {
            var intent = Intent(view.context, CourseDetailActivity::class.java)
            intent.putExtra("CourseId", course?.id)
            view.context.startActivity(intent)
        }

        companion object {
            // Add a key for easy reference to the item launching the RecyclerView.
            private val COURSE_KEY = "COURSE"
        }

    }

}