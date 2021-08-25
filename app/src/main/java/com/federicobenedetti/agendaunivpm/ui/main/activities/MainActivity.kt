package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.utils.ViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial


/**
 * Questa NON è l'Activity che viene lanciata all'avvio dell'app.
 * Questo perché, nell'architettura che ho ideato, la MainActivity si riferisce
 * alla Activity che viene mostrata una volta effettuato il login.
 */
class MainActivity : CustomAppCompatActivity("MAIN") {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabs)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)

        // Dico al TabLayout di assegnare ad ogni Fragment un'icona differente
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_person)
                }
                1 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_list)
                }
                2 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_bookmarks)
                }
                3 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_auto_stories)
                }
            }
        }.attach()
    }
}
