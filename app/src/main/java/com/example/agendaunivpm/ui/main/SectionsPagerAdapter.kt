package com.example.agendaunivpm.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.ListFragment
import com.example.agendaunivpm.ui.main.fragments.HomeFragment
import com.example.agendaunivpm.ui.main.fragments.SearchFragment
import com.example.agendaunivpm.ui.main.fragments.UserFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        when(position) {
            1 -> return HomeFragment();
            2 -> return ListFragment();
            3 -> return SearchFragment();
            4 -> return UserFragment();
        }
        return HomeFragment();
    }

    override fun getCount(): Int {
        // Show 4 total pages.
        return 4
    }
}