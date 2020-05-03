package com.boodabest.repositories.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.boodabest.models.LoginResponse
import com.boodabest.network.Resource
import javax.inject.Inject

class AuthViewModel @Inject constructor(authRepository: AuthRepository) : ViewModel() {
    private val _isAuth = MutableLiveData(false)
    private val _loginCredential: MutableLiveData<LoginCredential> = MutableLiveData()

    val isAuth: LiveData<Boolean>
        get() = _isAuth

    val loginItem: LiveData<Resource<LoginResponse>> = _loginCredential.switchMap { input ->
        input.ifExists { username, password ->
            authRepository.login(username, password)
        }
    }

    fun setLogin(username: String, password: String) {
        val update = LoginCredential(username, password)
        if (_loginCredential.value == update) {
            return
        }
        _loginCredential.value = update
    }


    data class LoginCredential(val username: String, val password: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return f(username, password)
        }
    }

}