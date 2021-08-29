package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.singletons.ActivityUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.FirebaseUtils
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Activity che permette all'utente di registrarsi / effettuare il login
 * Per ora, per semplicità, l'unico metodo di login a disposizione è attraverso l'auth di Google
 * In futuro, sarà facilmente estendibile a una login tradizionale tramite iscrizione (email, password, ...)
 * oppure altri vari metodi tipo apple, microsoft, ....
 */
class LoginActivity : CustomAppCompatActivity("LOGIN") {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    private var mButtonSignIn: SignInButton? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mButtonSignIn = findViewById(R.id.buttonSignIn)
        mButtonSignIn!!.setOnClickListener {
            startLoginProcedure()
        }

        // Ci serve per capire se c'è un utente loggato o meno
        val currentUser = FirebaseUtils.getFirebaseAuthInstance()!!.currentUser

        Log.w(_logTAG, "Current signedInUser: $currentUser")

        if (currentUser != null) {
            startDataLoadingActivity()
        }
    }

    /**
     * Avviamo la procedura di Login passandogli il Token di richiesta
     * generato dalla shell Firebase
     */
    private fun startLoginProcedure() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mFirebaseAuth = FirebaseUtils.getFirebaseAuthInstance()

        startActivityForResult(mGoogleSignInClient?.signInIntent, 9001)
    }

    /**
     * A questo punto siamo ritornati dalla Form di Login (classica Form Google
     * in cui ti chiede di scegliere il tuo account)
     *
     * Procedo a fare l'autenticazione con Firebase
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.w(_logTAG, "firebaseAuthWithGoogle: " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(_logTAG, "Google sign in failed", e)
                Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Usiamo l'autenticazione Firebase, metodo: Google
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mFirebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.w(_logTAG, "signInWithCredential success")
                    Log.w(_logTAG, "current signed in user " + mFirebaseAuth!!.currentUser)
                    startDataLoadingActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(_logTAG, "signInWithCredential failure", task.exception)
                    Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun startDataLoadingActivity() {
        ActivityUtils.launchActivity(this, DataLoadingActivity::class)
    }
}

