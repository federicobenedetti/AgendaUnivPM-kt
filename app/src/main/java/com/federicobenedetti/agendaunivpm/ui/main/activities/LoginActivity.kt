package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.singletons.*
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : CustomAppCompatActivity("LOGIN") {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    private var mButtonSignIn: SignInButton? = null

    private val RC_SIGN_IN = 9001

    private lateinit var linearLayoutLoading: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mButtonSignIn = findViewById(R.id.buttonSignIn)
        mButtonSignIn!!.setOnClickListener {
            linearLayoutLoading.visibility = View.VISIBLE
            startLoginProcedure()
        }

        linearLayoutLoading = findViewById(R.id.linear_layout_loading)

        var currentUser = FirebaseUtils.getFirebaseAuthInstance()!!.currentUser

        FirebaseUtils.setAuthStateListener(this)

        Log.w(_logTAG, "Current signedInUser: $currentUser")

        if (currentUser != null) {
            linearLayoutLoading.visibility = View.VISIBLE
            loadStudentData()
        }
    }

    // When we get back from the Google Login Form
    // we handle the response and update the UI
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.w(_logTAG, "firebaseAuthWithGoogle: " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(_logTAG, "Google sign in failed", e)
            }
        }
    }

    private fun startLoginProcedure() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mFirebaseAuth = FirebaseUtils.getFirebaseAuthInstance()

        startActivityForResult(mGoogleSignInClient?.signInIntent, RC_SIGN_IN)
    }

    // We're logging in with Firebase, Google auth
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mFirebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w(_logTAG, "signInWithCredential success")
                    Log.w(_logTAG, "current signed in user " + mFirebaseAuth!!.currentUser)
                    loadStudentData()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(_logTAG, "signInWithCredential failure", task.exception)
                }
            }
    }

    // Per il momento faremo qui tutte le nostre chiamate, una dietro l'altra
    // Carichiamo tutti i dati, dopodiché lanciamo la main activity
    // Non è proprio il massimo.. anzi
    // Però è l'unico modo (veloce) che mi è venuto in mente per far chiamate concatenate
    // aspettando il loro risultato.
    // Non ho un db locale o un backend "tradizionale", le API Firebase sono limitate
    // e le query concatenate sono definitivamente molto più complesse del necessario rispetto
    // allo scopo e alla quantità di dati richiesti per quest'app
    private fun loadStudentData() {
        // Carichiamo lo studente
        FirebaseService.getStudentByUid().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                var result = it.result

                WhoAmI.setLoggedInStudent(result)

                // Carichiamo tutti i corsi registrati
                FirebaseService.getStudentCourses(result.corsi).addOnCompleteListener {
                    if (it.isSuccessful) {


                        Logger.d(_logTAG, "Corsi trovati: ", it.result)
                        WhoAmI.setLoggedInStudentCourses(it.result)


                        // Carichiamo tutti i professori
                        FirebaseService.getTeachers().addOnCompleteListener {
                            if (it.isSuccessful) {

                                DataPersistanceUtils.setTeachers(it.result)

                                Logger.d(
                                    _logTAG,
                                    "Professori registrati: ",
                                    DataPersistanceUtils.getTeachers()
                                )

                                Toast.makeText(this, R.string.generic_success, Toast.LENGTH_LONG)
                                    .show();
                                ActivityUtils.launchActivity(this, MainActivity::class)
                                finish()
                                return@addOnCompleteListener
                            } else {
                                Logger.d(
                                    _logTAG,
                                    "Errore durante il retrieve dei teacher: " + it.exception
                                )
                                Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG)
                                    .show();
                                return@addOnCompleteListener
                            }


                        }


                        return@addOnCompleteListener
                    } else {
                        Logger.d(_logTAG, "Errore durante il retrieve dei corsi: " + it.exception)
                        Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show();
                        return@addOnCompleteListener
                    }
                }


            } else {
                Logger.d(_logTAG, "Errore durante il set della matricola: " + it.exception)
                Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show();
            }
        }

    }
}