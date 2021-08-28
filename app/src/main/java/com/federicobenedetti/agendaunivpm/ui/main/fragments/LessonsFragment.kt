package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.FragmentLessonsBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.utils.LessonRecyclerAdapter
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.LessonsViewModel

/**
 * Fragment che mostra a video la lista di tutti i corsi a cui l'utente può partecipare
 */
class LessonsFragment : CustomFragment("LESSONS") {
    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!

    private val _lessonsViewModel: LessonsViewModel by activityViewModels()


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mLessonRecyclerAdapter: LessonRecyclerAdapter
    private lateinit var mRecyclerViewLessonCard: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lessons, container, false)
        binding.lessonsViewModel = _lessonsViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerViewLessonCard = view.findViewById(R.id.mRecyclerViewLessonsCard)

        mRecyclerViewLessonCard.layoutManager = linearLayoutManager

        var lessons = _lessonsViewModel.getLessonsAsArrayList()

        mLessonRecyclerAdapter = LessonRecyclerAdapter(lessons)
        mRecyclerViewLessonCard.adapter = mLessonRecyclerAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = LessonsFragment()
    }
}