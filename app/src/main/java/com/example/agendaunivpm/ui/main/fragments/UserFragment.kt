package com.example.agendaunivpm.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.agendaunivpm.R
import com.example.agendaunivpm.databinding.FragmentUserBinding
import com.example.agendaunivpm.ui.main.viewmodels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class UserFragment : Fragment() {
    // We are using ViewBinding here
    // as we dont have dynamic data to handle
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    // UserFragment viewmodel
    private val _userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        binding.userViewModel = _userViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        getCurrentSignedInUser()

        return view
    }

    private fun getCurrentSignedInUser() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account == null) {
            signInProcedure()
        } else {
            retrieveSignedInAccount()
        }
    }

    private fun retrieveSignedInAccount() {

    }

    private fun signInProcedure() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        var mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) };
        var signInIntent = mGoogleSignInClient?.signInIntent


        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode === 1) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(signInIntent)
                handleSignInResult(task)
            }
        }.launch(signInIntent)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("PIPPO", "signInResult:failed code=" + e.statusCode)
            // updateUI(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}