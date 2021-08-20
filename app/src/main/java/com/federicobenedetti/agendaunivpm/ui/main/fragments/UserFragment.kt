package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.os.Bundle
import android.util.Log
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
import com.federicobenedetti.agendaunivpm.databinding.FragmentUserBinding
import com.federicobenedetti.agendaunivpm.ui.main.activities.FaqActivity
import com.federicobenedetti.agendaunivpm.ui.main.activities.FeedbackActivity
import com.federicobenedetti.agendaunivpm.ui.main.activities.LoginActivity
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class UserFragment : CustomFragment("USER") {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val _userViewModel: UserViewModel by activityViewModels()

    private var mFirebaseAuth: FirebaseAuth? = null

    private var mButtonFaqActivity: Button? = null
    private var mButtonFeedbackActivity: Button? = null
    private var mButtonSignOut: Button? = null
    private var mImageViewUserProfileImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        binding.userViewModel = _userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        _userViewModel.loggedInUser!!.observe(viewLifecycleOwner, Observer {
            mImageViewUserProfileImage?.let { it1 ->
                Glide.with(this).load(_userViewModel.loggedInUser!!.value!!.getPhotoUrl())
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
            mFirebaseAuth!!.signOut()
        }

        mFirebaseAuth = FirebaseUtils!!.getFirebaseAuthInstance()

        mFirebaseAuth!!.addAuthStateListener {
            Log.w(_logTAG, "FirebaseAuth state changed")
            if (mFirebaseAuth!!.currentUser == null) {
                Log.w(_logTAG, "User logged out")
                context?.let { it -> ActivityUtils.launchActivity(it, LoginActivity::class) }
            }
        }

        mImageViewUserProfileImage = binding.userProfileImage

        _userViewModel.setCurrentLoggedInUserMatricola(WhoAmI.getStudentMatricola())
        return view
    }

    // Let's check that we have already a user logged in
    override fun onStart() {
        super.onStart()

        val currentUser = mFirebaseAuth!!.currentUser
        _userViewModel.setCurrentLoggedInUser(currentUser)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}
