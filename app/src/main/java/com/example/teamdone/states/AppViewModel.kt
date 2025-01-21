package com.example.teamdone.states

import androidx.lifecycle.ViewModel
import com.example.teamdone.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private val _isUserLoggedIn = MutableStateFlow(false)
    private val _user = MutableStateFlow<User?>(null)
    val isUserLoggedIn: StateFlow<Boolean> get() = _isUserLoggedIn
    val user: StateFlow<User?> get() = _user;

    fun setUser(user: User?) {
        _user.value = user
    }

    fun login() {
        _isUserLoggedIn.value = true
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _isUserLoggedIn.value = false
    }

    fun autoLogin() {
        val fbUser = FirebaseAuth.getInstance().currentUser
        val firestore = FirebaseFirestore.getInstance()

        login()

        fbUser?.let {
            firestore
                .collection("users")
                .document(it.uid)
                .get()
                .addOnCompleteListener { t ->
                    if(t.isSuccessful) {
                        User.fromMap(t.result.data)?.let { u ->
                            _user.value = u
                        }
                    }
                }
        }
    }

    fun ladCurrentUser() {
        val fbUser = FirebaseAuth.getInstance().currentUser
        val firestore = FirebaseFirestore.getInstance()

        fbUser?.let {
            firestore
                .collection("users")
                .document(it.uid)
                .get()
                .addOnCompleteListener { t ->
                    if(t.isSuccessful) {
                        User.fromMap(t.result.data)?.let { u ->
                            _user.value = u
                        }
                    }
                }
        }
    }

}