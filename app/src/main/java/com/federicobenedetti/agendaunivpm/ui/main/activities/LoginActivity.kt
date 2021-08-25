package com.federicobenedetti.agendaunivpm.ui.main.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.federicobenedetti.agendaunivpm.R
import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Lesson
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.federicobenedetti.agendaunivpm.ui.main.classes.Teacher
import com.federicobenedetti.agendaunivpm.ui.main.singletons.*
import com.federicobenedetti.agendaunivpm.ui.main.utils.CustomAppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : CustomAppCompatActivity("LOGIN") {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null

    private var mButtonSignIn: SignInButton? = null

    private lateinit var linearLayoutLoading: LinearLayout

    private var loadingStudentCourses: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mButtonSignIn = findViewById(R.id.buttonSignIn)
        mButtonSignIn!!.setOnClickListener {
            linearLayoutLoading.visibility = View.VISIBLE
            startLoginProcedure()
        }

        linearLayoutLoading = findViewById(R.id.linear_layout_loading)

        // Ci serve per capire se c'è un utente loggato o meno
        val currentUser = FirebaseUtils.getFirebaseAuthInstance()!!.currentUser

        // Se siamo nella LoginActivity, voglio che la nostra Utils di Firebase faccia l'init
        // del suo AuthListener
        // In modo che se cambiasse mai qualche cosa (tipo l'utente fa il logout)
        // a quel punto ripartirebbe la LoginActivity
        // (stiamo passando il contesto al metodo, non è la best-practice ma per ora può funzionare)
        FirebaseUtils.setAuthStateListener(this)

        Log.w(_logTAG, "Current signedInUser: $currentUser")

        if (currentUser != null) {
            // La loading deve apparire nel momento in cui
            // decidiamo di caricare i dati dello studente (già loggato)
            linearLayoutLoading.visibility = View.VISIBLE
            loadStudentData()
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
                    loadStudentData()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(_logTAG, "signInWithCredential failure", task.exception)
                }
            }
    }


    /**
     * Partiamo dal caricare lo studente che sta facendo la login
     */
    private fun loadStudentData() {
        FirebaseService.getStudentByUid()
            .addOnCompleteListener { task -> handleOnCompleteListener(task) }
    }

    /**
     * Questo è un metodo quasi ricorsivo
     * Abbiamo una condizione di 'break' che è quella che si innesca
     * quando stiamo caricando tutti i corsi, a quel punto lanciamo la
     * MainActivity e chiudiamo la LoginActivity
     *
     * Per i restanti casi facciamo type-checking del risultato
     * del onCompleteListener, e in base a quello concateniamo altre chiamate
     */
    private fun handleOnCompleteListener(task: Task<*>) {
        if (task.isSuccessful) {

            when (task.result) {
                // 5: Carichiamo tutte le lezioni
                is Lesson -> {
                    Logger.d(_logTAG, "La lista è di Lezioni")

                    val lessons = task.result as List<Lesson>

                    Logger.d(_logTAG, "Risultato chiamata getLessons", lessons)

                    DataPersistanceUtils.setLessons(lessons)

                    FirebaseService.getCourses()
                        .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                }

                is Student -> {
                    // 1: Carichiamo lo studente
                    val student = task.result as Student

                    Logger.d(_logTAG, "Risultato chiamata getStudentByUid", student)

                    WhoAmI.setLoggedInStudent(student)

                    FirebaseService.getStudentCourses(student.corsi)
                        .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                }

                is List<*> -> {
                    Logger.d(_logTAG, "E' stata ricevuta una lista")
                    if ((task.result as List<*>).all { e -> e is Course }) {
                        Logger.d(_logTAG, "La lista è di Corsi")

                        // 2: Carichiamo i corsi dello studente
                        if (loadingStudentCourses) {
                            Logger.d(_logTAG, "Stiamo caricando i corsi dello studente")

                            val courses = task.result as List<Course>

                            Logger.d(_logTAG, "Risultato chiamata getStudentCourses", courses)

                            WhoAmI.setLoggedInStudentCourses(courses)

                            loadingStudentCourses = false
                            FirebaseService.getTeachers()
                                .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                        } else {
                            // 4: Carichiamo tutti i corsi

                            Logger.d(_logTAG, "Stiamo caricanto tutti i corsi")

                            val allCourses = task.result as List<Course>

                            Logger.d(_logTAG, "Risultato chiamata getCourses", allCourses)

                            DataPersistanceUtils.setCourses(allCourses)


                            Toast.makeText(
                                this,
                                R.string.generic_success,
                                Toast.LENGTH_LONG
                            )
                                .show()

                            loadingStudentCourses = true

                            Logger.d(_logTAG, "Finito! Lancio la MainActivity")
                            ActivityUtils.launchActivity(this, MainActivity::class)
                            finish()
                        }


                    } else if ((task.result as List<*>).all { e -> e is Teacher }) {
                        Logger.d(_logTAG, "La lista è di Professori")

                        // 3: Carichiamo tutti i professori
                        val teachers = task.result as List<Teacher>

                        Logger.d(_logTAG, "Risultato chiamata getTeachers", teachers)

                        DataPersistanceUtils.setTeachers(teachers)
                        FirebaseService.getLessons()
                            .addOnCompleteListener { task -> handleOnCompleteListener(task) }
                    }
                }
            }

        } else {
            Logger.d(_logTAG, "Errore durante la chiamata", task.exception)
            Toast.makeText(this, R.string.generic_error, Toast.LENGTH_LONG).show()
            linearLayoutLoading.visibility = View.GONE
        }

    }
}

