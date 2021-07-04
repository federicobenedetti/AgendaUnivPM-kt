package com.example.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.agendaunivpm.databinding.FragmentSearchBinding
import com.example.agendaunivpm.databinding.FragmentUserBinding
import com.example.agendaunivpm.ui.main.viewmodels.SearchViewModel
import com.example.agendaunivpm.ui.main.viewmodels.UserViewModel

class SearchFragment: Fragment() {
    private var searchViewModel: SearchViewModel? = null

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun newInstance(): UserFragment? {
        return UserFragment()
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}