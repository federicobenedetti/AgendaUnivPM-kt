package com.federicobenedetti.agendaunivpm.ui.main.singletons

import android.content.Context
import com.federicobenedetti.agendaunivpm.ui.main.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

/**
 * Singleton utilizzato per mantenere un'istanza unica
 * di vari agganci alla parte di Firebase.
 */
object FirebaseUtils {
    // Unica istanza dell'auth di Firebase
    private var mFirebaseAuth: FirebaseAuth? = null

    // Unica istanza delle Firebase Functions
    private var mFirebaseFunctions: FirebaseFunctions? = null

    // Unica istanza di Firebase Storage
    private var mFirebaseStorage: FirebaseStorage? = null

    private var mStorageRef: StorageReference? = null

    private var mStreamingPath: String = "gs://agenda-univpm.appspot.com/01.mp4"

    // Mi serve per capire se l'auth listener è stato impostato (dalla Login Activity)
    // Altrimenti si rischia un loop
    private var authListenerSet: Boolean = false

    init {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseFunctions = Firebase.functions
        mFirebaseStorage = Firebase.storage
        mStorageRef = mFirebaseStorage!!.getReferenceFromUrl(mStreamingPath)
    }

    /**
     * Imposto l'authListener dalla LoginActivity
     * Questo mi permette di ricaricarla se l'utente dovesse fare il logout
     */
    fun setAuthStateListener(context: Context) {
        if (authListenerSet == false) {
            mFirebaseAuth!!.addAuthStateListener {

                // TODO: Controllare che cosa sta per fare l'utente
                // Se sta per fare il sign in, allora non ricreare la loginactivity (magari c'è un momento in cui il current user è null anche se sta facendo il sign in)
                if (mFirebaseAuth!!.currentUser == null) {
                    ActivityUtils.launchActivity(context, LoginActivity::class)
                }
            }
        }

        authListenerSet = true
    }

    /**
     * Ritorno l'unica istanza disponibile per l'auth di Firebase
     */
    fun getFirebaseAuthInstance(): FirebaseAuth? {
        return mFirebaseAuth
    }

    /**
     * Ritorno l'unica istanza disponibile per le Firebase Functions
     */
    fun getFirebaseFunctionsInstance(): FirebaseFunctions? {
        return mFirebaseFunctions
    }

    fun downloadStreamingVideo() {
        val localFile = File.createTempFile("streaming01", "mp4")
        mStorageRef?.getFile(localFile)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Logger.d("pippo", "successo")
            } else {
                Logger.d("pippo", "fallimento")
            }
        }
    }
}