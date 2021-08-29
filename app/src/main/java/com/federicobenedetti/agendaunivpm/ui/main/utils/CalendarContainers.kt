package com.federicobenedetti.agendaunivpm.ui.main.utils

import android.view.View
import android.widget.TextView
import com.federicobenedetti.agendaunivpm.R
import com.kizitonwose.calendarview.ui.ViewContainer

/**
 * DayView container
 * specifico per la libreria che fornisce la CalendarView
 * Serve per poter customizzare a proprio piacimento la vista per il giorno visualizzato in griglia
 */
class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)
}

/**
 * DayView container
 * specifico per la libreria che fornisce la CalendarView
 * Serve per poter customizzare a proprio piacimento la vista per il mese visualizzato in griglia
 */
class MonthViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.headerTextView)
}