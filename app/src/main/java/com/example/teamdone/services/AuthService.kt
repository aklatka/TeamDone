package com.example.teamdone.services

import android.util.Log
import com.example.teamdone.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random

class AuthService {
    private val TAG = "AuthService: ";

    fun createUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        onSuccess: (user: User) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {

                    val uid = task.result?.user?.uid

                    if(uid != null) {

//                        val avatarSeed = md5(firstname.trim().capitalize(Locale.getDefault()))
                        val avatarUrl = generateAvatarUrl(seed = firstname.trim())

                        val username = generateUsername(firstname, lastname)
                        Log.d(TAG, "createUser: $uid")

                        val userData = hashMapOf<String, Any>(
                            "uid" to uid,
                            "firstname" to firstname,
                            "lastname" to lastname,
                            "email" to email,
                            "username" to username,
                            "avatarUrl" to avatarUrl
                        )

                        firestore
                            .collection("users")
                            .document(uid)
                            .set(userData)
                            .addOnSuccessListener { docRef ->
                                User.fromMap(userData)?.let { onSuccess(it) }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "createUser: Error adding user doc", e)
                            }
                    }
                }
            }
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (user: User) -> Unit,
        onFailure: (error: Exception) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                val uid = task.result?.user?.uid

                if(uid != null) {

                    firestore
                        .collection("users")
                        .document(uid)
                        .get()
                        .addOnCompleteListener { t ->
                            if(t.isSuccessful) {
                                User.fromMap(t.result.data)?.let { onSuccess(it) }
                            }
                        }
                }
            }
        }.addOnFailureListener {
            onFailure(it)
        };
    }

    private fun generateUsername(firstname: String, lastname: String): String {
        val sanitizedFirstname = firstname.lowercase().replace(" ", "")
        val sanitizedLastname = lastname.lowercase().replace(" ", "")
        val suffix = Random.nextInt(1000, 9999)

        return sanitizedFirstname.substring(0, 3) + sanitizedLastname.substring(0, 3) + suffix.toString()
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    private fun generateAvatarUrl(seed: String): String {
        val baseUrl = "https://api.dicebear.com/9.x"
        val style = "initials"
        return "$baseUrl/$style/png?seed=$seed&fontFamily=Verdana&fontWeight=200&chars=1"
    }
}