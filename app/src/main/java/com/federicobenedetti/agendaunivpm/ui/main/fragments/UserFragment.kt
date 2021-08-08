package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.content.Intent
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
import com.federicobenedetti.agendaunivpm.ui.main.activities.MainActivity
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.utils.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


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
            launchFaqActivity()
        }

        mButtonFeedbackActivity = binding.buttonLaunchFeedbackActivity
        mButtonFeedbackActivity!!.setOnClickListener {
            launchFeedbackActivity()
        }

        mButtonSignOut = binding.buttonLaunchSignOut
        mButtonSignOut!!.setOnClickListener {
            signOut()
        }

        mFirebaseAuth = FirebaseUtils!!.getFirebaseAuthInstance()

        mFirebaseAuth!!.addAuthStateListener {
            Log.w(_logTAG, "FirebaseAuth state changed")
            if (mFirebaseAuth!!.currentUser == null) {
                Log.w(_logTAG, "User logged out")
                launchMainActivity()
            }
        }

        mImageViewUserProfileImage = binding.userProfileImage
        return view
    }

    // Let's check that we have already a user logged in
    override fun onStart() {
        super.onStart()

        val currentUser = mFirebaseAuth!!.currentUser
        updateUI(currentUser)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Notify the VM that data has changed and need to be refreshed
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.w(_logTAG, "User: " + user.email + ", " + user.displayName + ", " + user.photoUrl)
            _userViewModel.setCurrentLoggedInUser(user)
        }
    }

    private fun signOut() {
        Log.w(_logTAG, "Signing out of Firebase")
        mFirebaseAuth!!.signOut()
    }

    private fun launchFaqActivity() {
        val intent = Intent(context, FaqActivity::class.java)
        startActivity(intent)
    }

    private fun launchMainActivity() {
        Log.w(_logTAG, "Launching MainActivity")
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    private fun launchFeedbackActivity() {
        val intent = Intent(context, FeedbackActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}
