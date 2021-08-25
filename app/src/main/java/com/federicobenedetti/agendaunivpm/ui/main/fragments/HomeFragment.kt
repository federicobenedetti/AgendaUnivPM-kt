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
import com.federicobenedetti.agendaunivpm.databinding.FragmentHomeBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CourseRecyclerAdapter
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.HomeViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment : CustomFragment("HOME") {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val _homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mCourseRecyclerAdapter: CourseRecyclerAdapter
    private lateinit var mRecyclerViewInfoCard: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeViewModel = _homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        linearLayoutManager = LinearLayoutManager(context)

        mRecyclerViewInfoCard = view.findViewById(R.id.mRecyclerViewHomeInfoCard)

        mRecyclerViewInfoCard.layoutManager = linearLayoutManager

        var courses = _homeViewModel.getCoursesAsArrayList()

        mCourseRecyclerAdapter = CourseRecyclerAdapter(courses)
        mRecyclerViewInfoCard.adapter = mCourseRecyclerAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}