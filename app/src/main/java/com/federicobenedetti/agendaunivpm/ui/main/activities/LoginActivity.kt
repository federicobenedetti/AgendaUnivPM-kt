package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.gson.Gson
import com.google.gson.JsonElement

class LoginActivity : CustomAppCompatActivity("LOGIN") {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    private var mButtonSignIn: SignInButton? = null

    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mButtonSignIn = findViewById(R.id.buttonSignIn)
        mButtonSignIn!!.setOnClickListener {
            startLoginProcedure()
        }

        var currentUser = FirebaseUtils.getFirebaseAuthInstance()!!.currentUser

        Log.w(_logTAG, "Current signedInUser: $currentUser")

        if (currentUser != null) {
            launchMainActivity()
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
                    launchMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(_logTAG, "signInWithCredential failure", task.exception)
                }
            }
    }

    private fun setStudentMatricola() {
        askForStudentByUid()
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    val e = task.exception
                    if (e is FirebaseFunctionsException) {
                        val code = e.code
                        val details = e.details

                        Log.w(_logTAG, "Failure code: $code")
                        Log.w(_logTAG, "Failure details: $details")
                    }

                    Log.w(_logTAG, "addMessage:onFailure", e)
                    return@OnCompleteListener
                }

                /**
                 * Se il risultato della chiamata è null c'è qualche problema grosso dietro
                 * Evidentemente all'utente loggato non gli è stata assegnata una matricola (Firebase)
                 * quindi è necessario riverificarlo, facendolo ripassare dal login
                 */

                /**
                 * Se il risultato della chiamata è null c'è qualche problema grosso dietro
                 * Evidentemente all'utente loggato non gli è stata assegnata una matricola (Firebase)
                 * quindi è necessario riverificarlo, facendolo ripassare dal login
                 */
                if (task.result == null) {
                    Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show();
                    // startLoginActivity()
                    // finish()

                    return@OnCompleteListener
                }

                WhoAmI.setLoggedInStudent(task.result!!)
                return@OnCompleteListener
            })
    }

    private fun askForStudentByUid(): Task<Student?> {
        return FirebaseUtils.getFirebaseFunctionsInstance()!!
            .getHttpsCallable("getUserFromAuthUid")
            .call()
            .continueWith { task ->
                val result = task.result?.data as Map<String, Any>

                val gson = Gson()
                val jsonElement: JsonElement = gson.toJsonTree(result)
                val pojo: Student = gson.fromJson(jsonElement, Student::class.java)

                Log.d(_logTAG, "Student: ${pojo.matricola}")

                pojo

            }
    }

    fun launchMainActivity() {
        setStudentMatricola()

        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

}