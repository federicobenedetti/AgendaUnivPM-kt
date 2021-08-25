package com.federicobenedetti.agendaunivpm.ui.main.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.federicobenedetti.agendaunivpm.ui.main.fragments.CoursesListFragment
import com.federicobenedetti.agendaunivpm.ui.main.fragments.LessonsFragment
import com.federicobenedetti.agendaunivpm.ui.main.fragments.SubscribedCoursesFragment
import com.federicobenedetti.agendaunivpm.ui.main.fragments.UserProfileFragment

class ViewPagerFragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return 4;
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserProfileFragment.newInstance()
            1 -> CoursesListFragment.newInstance()
            2 -> SubscribedCoursesFragment.newInstance()
            3 -> LessonsFragment.newInstance()
            else -> SubscribedCoursesFragment.newInstance()
        }
    }
}