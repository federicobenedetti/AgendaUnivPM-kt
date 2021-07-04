package com.example.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.agendaunivpm.R
import com.example.agendaunivpm.databinding.FragmentSearchBinding
import com.example.agendaunivpm.databinding.FragmentUserBinding
import com.example.agendaunivpm.ui.main.viewmodels.SearchViewModel
import com.example.agendaunivpm.ui.main.viewmodels.UserViewModel

class SearchFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}