package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.federicobenedetti.agendaunivpm.ui.main.fragments.HomeFragment
import com.federicobenedetti.agendaunivpm.ui.main.fragments.ListFragment
import com.federicobenedetti.agendaunivpm.ui.main.fragments.SearchFragment
import com.federicobenedetti.agendaunivpm.ui.main.fragments.UserFragment

class ViewPagerFragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return 4;
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserFragment.newInstance()
            1 -> ListFragment.newInstance()
            2 -> SearchFragment.newInstance()
            3 -> HomeFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }
}