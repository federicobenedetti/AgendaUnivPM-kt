import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.activities.LessonDetailActivity
import com.federicobenedetti.agendaunivpm.ui.main.classes.CalendarLesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils

class CalendarLessonsAdapter(val lessons: MutableList<CalendarLesson>) :
    RecyclerView.Adapter<CalendarLessonsAdapter.CalendarLessonsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CalendarLessonsViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_calendar_lesson_view, parent, false)
        return CalendarLessonsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(viewHolder: CalendarLessonsViewHolder, position: Int) {
        viewHolder.bind(lessons[position])
    }

    override fun getItemCount(): Int = lessons.size

    class CalendarLessonsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var viewLesson: Lesson

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            ActivityUtils.launchActivityWithParams(
                itemView.context,
                LessonDetailActivity::class,
                hashMapOf(
                    "LessonId" to viewLesson.id
                )
            )
        }

        fun bind(lesson: CalendarLesson) {
            // Siamo sicuri che la lezione non sarà mai null in quanto
            // la sincronia viene fatta all'inizio
            // i dati NON possono cambiare (con l'attuale architettura dell'app) ammenoché non
            // si refreshi, ma comunque rimarrebbero coerenti
            // e dalla lista sto cliccando un elemento
            var lesson = DataPersistanceUtils.getLessonById(lesson.lessonId)

            if (lesson != null) {
                viewLesson = lesson
                itemView.findViewById<TextView>(R.id.itemEventText).text = lesson!!.title
            }
        }

    }
}