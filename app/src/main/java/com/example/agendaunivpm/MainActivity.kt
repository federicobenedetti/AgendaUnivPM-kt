package com.example.agendaunivpm

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.agendaunivpm.ui.main.SectionsPagerAdapter
import com.example.agendaunivpm.databinding.ActivityMainBinding
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mi ritorna i fragment delle pagine che poi andrò a visualizzare
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        // viewPager: mi serve per poter mostrare le pagine swipando
        val viewPager: ViewPager = binding.viewPager

        viewPager.adapter = sectionsPagerAdapter

        // Tab layout
        val tabs: TabLayout = binding.tabLayout
        tabs.setupWithViewPager(viewPager)

        // Setup our tabitems layout with icons
        for(index in 0..tabs.tabCount) {
            var tab = tabs.getTabAt(index)
            when (index) {
                0 -> {
                    tab?.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_home_filled)
                }
                1 -> {
                    tab?.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_list)
                }
                2 -> {
                    tab?.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_search)
                }
                3 -> {
                    tab?.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_person)
                }
            }

        }
    }
}