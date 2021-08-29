package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.FragmentUserProfileBinding
import com.federicobenedetti.agendaunivpm.ui.main.activities.CourseCalendarActivity
import com.federicobenedetti.agendaunivpm.ui.main.activities.FaqActivity
import com.federicobenedetti.agendaunivpm.ui.main.activities.FeedbackActivity
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Fragment che mostra a video i dati dell'utente loggato
 */
class UserProfileFragment : CustomFragment("USER") {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    private val _userProfileViewModel: UserProfileViewModel by activityViewModels()

    private var mFirebaseAuth: FirebaseAuth? = null

    private var mButtonFaqActivity: Button? = null
    private var mButtonFeedbackActivity: Button? = null
    private var mButtonSignOut: Button? = null
    private var mButtonCalendar: Button? = null

    private var mImageViewUserProfileImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        binding.userViewModel = _userProfileViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        _userProfileViewModel.loggedInUser!!.observe(viewLifecycleOwner, Observer {
            mImageViewUserProfileImage?.let { it1 ->
                Glide.with(this).load(_userProfileViewModel.loggedInUser!!.value!!.getPhotoUrl())
                    .circleCrop()
                    .into(it1)
            };
        })

        mButtonFaqActivity = binding.buttonLaunchFaqActivity
        mButtonFaqActivity!!.setOnClickListener {
            context?.let { it -> ActivityUtils.launchActivity(it, FaqActivity::class) }
        }

        mButtonFeedbackActivity = binding.buttonLaunchFeedbackActivity
        mButtonFeedbackActivity!!.setOnClickListener {
            context?.let { it -> ActivityUtils.launchActivity(it, FeedbackActivity::class) }
        }

        mButtonSignOut = binding.buttonLaunchSignOut
        mButtonSignOut!!.setOnClickListener {
            FirebaseUtils.signOut()
        }

        mButtonCalendar = binding.buttonLaunchCalendar
        mButtonCalendar!!.setOnClickListener {
            context?.let { it -> ActivityUtils.launchActivity(it, CourseCalendarActivity::class) }
        }

        mFirebaseAuth = FirebaseUtils!!.getFirebaseAuthInstance()

        mImageViewUserProfileImage = binding.userProfileImage

        _userProfileViewModel.setCurrentLoggedInUserMatricola(WhoAmI.getStudentMatricola())
        _userProfileViewModel.setCurrentLoggedInUserPhoneNumber(WhoAmI.getStudentPhoneNumber())
        _userProfileViewModel.setCurrentLoggedInUserCourseYear(WhoAmI.getStudentCourseYear())
        _userProfileViewModel.setCurrentLoggedInUserSituazioneTasse(WhoAmI.getStudentSituazioneTasse())
        return view
    }

    // Let's check that we have already a user logged in
    override fun onStart() {
        super.onStart()

        val currentUser = mFirebaseAuth!!.currentUser
        if (currentUser != null) {
            _userProfileViewModel.setCurrentLoggedInUser(currentUser)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserProfileFragment()
    }
}
