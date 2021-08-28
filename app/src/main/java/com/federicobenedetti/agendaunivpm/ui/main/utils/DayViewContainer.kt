package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.view.View
import android.widget.TextView
import com.federicobenedetti.agendaunivpm.R
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)

    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}