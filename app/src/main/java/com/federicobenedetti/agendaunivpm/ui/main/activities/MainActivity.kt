package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.utils.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.utils.ViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial


class MainActivity : CustomAppCompatActivity("MAIN") {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private var LAUNCH_LOGIN_ACTIVITY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.w(_logTAG, "Starting MainActivity")

        var currentUser = FirebaseUtils.getFirebaseAuthInstance()!!.currentUser

        Log.w(_logTAG, "Current signedInUser: $currentUser")

        if (currentUser != null) {
            initViewPager()
        } else {
            startLoginActivity();
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.w(_logTAG, "ActivityResult reached, requestCode: $requestCode")

        if (requestCode == LAUNCH_LOGIN_ACTIVITY) {
            Log.w(_logTAG, "Request code is equal to LAUNCH_LOGIN_ACTIVITY")
            var currentUser = FirebaseUtils.getFirebaseAuthInstance()!!.currentUser;
            Log.w(_logTAG, "After LoginActivity, current signed in user: $currentUser")

            // TODO: Maybe there's a better way to do the same check
            if (currentUser == null) {

                Log.w(_logTAG, "There's no available signed in user, asking LoginActivity again...")
                startLoginActivity();
            } else {

                Log.w(_logTAG, "User successfully signed in, creating view pager")
                initViewPager();
            }
        }
    }

    private fun startLoginActivity() {
        Log.w(_logTAG, "User not logged in, asking for LoginActivity")
        var intent = Intent(this, LoginActivity::class.java);
        startActivityForResult(intent, LAUNCH_LOGIN_ACTIVITY);
    }

    private fun initViewPager() {
        Log.w(_logTAG, "User logged in, init View Pager")
        tabLayout = findViewById(R.id.tabs)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_person)
                }
                1 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_list)
                }
                2 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_search)
                }
                3 -> {
                    tab.icon = IconicsDrawable(this, GoogleMaterial.Icon.gmd_home_filled)
                }
            }
        }.attach()
    }
}