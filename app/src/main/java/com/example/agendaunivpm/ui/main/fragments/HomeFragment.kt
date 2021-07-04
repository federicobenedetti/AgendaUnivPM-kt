package com.example.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.agendaunivpm.databinding.FragmentHomeBinding
import com.example.agendaunivpm.ui.main.viewmodels.HomeViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment : Fragment() {

    // ViewModel is the abstract class which
    // our viewmodels derives from
    // it should be okay as we're going to dynamically
    // change the loaded fragment / vm
    private lateinit var pageViewModel: ViewModel

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}