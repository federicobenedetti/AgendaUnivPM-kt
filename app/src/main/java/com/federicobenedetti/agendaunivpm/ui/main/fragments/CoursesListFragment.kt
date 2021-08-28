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
import com.federicobenedetti.agendaunivpm.databinding.FragmentCoursesListBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CourseRecyclerAdapter
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.CoursesListViewModel

/**
 * Fragment che mostra a schermo la lista di tutti i corsi disponibili nell'app a cui
 * l'utente può iscriversi
 */
class CoursesListFragment : CustomFragment("COURSESLIST") {
    private var _binding: FragmentCoursesListBinding? = null
    private val binding get() = _binding!!

    private val _coursesListViewModel: CoursesListViewModel by activityViewModels()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mCourseRecyclerAdapter: CourseRecyclerAdapter
    private lateinit var mRecyclerViewInfoCard: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_courses_list, container, false)
        binding.coursesListViewModel = _coursesListViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerViewInfoCard = view.findViewById(R.id.mRecyclerViewListInfoCard)

        mRecyclerViewInfoCard.layoutManager = linearLayoutManager

        var courses = _coursesListViewModel.getCoursesAsArrayList()

        mCourseRecyclerAdapter = CourseRecyclerAdapter(courses)
        mRecyclerViewInfoCard.adapter = mCourseRecyclerAdapter


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoursesListFragment()
    }
}