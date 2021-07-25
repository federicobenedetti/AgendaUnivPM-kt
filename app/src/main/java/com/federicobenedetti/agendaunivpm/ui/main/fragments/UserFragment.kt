package com.federicobenedetti.agendaunivpm.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.databinding.FragmentUserBinding
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomFragment
import com.federicobenedetti.agendaunivpm.ui.main.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class UserFragment : CustomFragment("USER") {
    // We are using ViewBinding here
    // as we dont have dynamic data to handle
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    // UserFragment viewmodel
    private val _userViewModel: UserViewModel by activityViewModels()

    private var mFirebaseAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        binding.userViewModel = _userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root


        signIn()

        return view
    }

    override fun onStart() {
        super.onStart()

        val currentUser = mFirebaseAuth?.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        startActivityForResult(mGoogleSignInClient?.signInIntent, RC_SIGN_IN)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.w(_logTAG, "onCreate")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

        mFirebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.w(_logTAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(_logTAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            mFirebaseAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(_logTAG, "signInWithCredential:success")
                        val user = mFirebaseAuth?.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(_logTAG, "signInWithCredential:failure", task.exception)
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Log.w(_logTAG, "User: " + user.email + ", " + user.displayName + ", " + user.photoUrl)
            _userViewModel.setCurrentLoggedInUser(user)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
        private const val RC_SIGN_IN = 9001
    }
}
