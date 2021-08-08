package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.FragmentListBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.ListViewModel

class ListFragment : CustomFragment("LIST") {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    
    private val _listViewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.listViewModel = _listViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}