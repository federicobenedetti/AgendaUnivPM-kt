package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.FragmentLessonsBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment

class LessonsFragment : CustomFragment("SEARCH") {

    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lessons, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = LessonsFragment()
    }
}