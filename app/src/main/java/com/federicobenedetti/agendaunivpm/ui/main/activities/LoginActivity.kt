package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.singletons.*
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.federicobenedetti.agendaunivpm.ui.main.utils.ExceptionHandler
import com.google.android.gms.auth.api.signin.GoogleSignIn
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


    private var mButtonSignIn: SignInButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mButtonSignIn = findViewById(R.id.buttonSignIn)
        mButtonSignIn!!.setOnClickListener {
            startLoginProcedure()
        }

        /**
         * Puliamo tutto prima di procedere alla login
         */
        FirebaseUtils.initFirebase()
        DataPersistanceUtils.reset()
        WhoAmI.reset()

        /**
         * La login activity è la prima activity chiamata. Impostiamo il contesto per il nostro
         * custom exception handler qui, in modo che poi venga rilanciata se l'app dovesse
         * crashare
         */
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(this))
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
        FirebaseUtils.setGoogleSignInClient(GoogleSignIn.getClient(this, gso))
        
        mFirebaseAuth = FirebaseUtils.getFirebaseAuthInstance()

        startActivityForResult(FirebaseUtils.getGoogleSignInClientIntent(), 9001)
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
                Logger.d(_logTAG, "firebaseAuthWithGoogle: " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Logger.d(_logTAG, "Google sign in failed", e)
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
                    Logger.d(_logTAG, "signInWithCredential success")
                    Logger.d(_logTAG, "current signed in user " + mFirebaseAuth!!.currentUser)
                    startDataLoadingActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Logger.d(_logTAG, "signInWithCredential failure", task.exception)
                    Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun startDataLoadingActivity() {
        ActivityUtils.launchActivity(this, DataLoadingActivity::class)
    }
}

