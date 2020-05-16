package com.boodabest.repositories.auth

import androidx.lifecycle.*
import com.boodabest.database.User
import com.boodabest.models.LoginResponse
import com.boodabest.network.Resource
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _isAuth = authRepository.me().map {
        it != null
    }
    private val _loginCredential: MutableLiveData<LoginCredential> = MutableLiveData()
    private val _login: MutableLiveData<FetchMe> = MutableLiveData()

    val isAuth: LiveData<Boolean>
        get() = _isAuth

    val fetchMeItem: LiveData<Resource<User>> = _login.switchMap { input ->
        input.ifExists { login ->
            authRepository.fetchMe(login)
        }
    }

    val me: LiveData<Resource<User>> = authRepository.fetchMe()

    val loginItem: LiveData<Resource<LoginResponse>> = _loginCredential.switchMap { input ->
        input.ifExists { username, password ->
            authRepository.login(username, password)
        }
    }

    fun logout() {
        authRepository.logout()
    }

    fun setLogin(username: String, password: String) {
        val update = LoginCredential(username, password)
        if (_loginCredential.value == update) {
            return
        }
        _loginCredential.value = update
    }

    fun fetchMe(login: LoginResponse) {
        val update = FetchMe(login)
        if (_login.value == update) {
            return
        }
        _login.value = update
    }

    data class FetchMe(val login: LoginResponse) {
        fun <T> ifExists(f: (LoginResponse) -> LiveData<T>): LiveData<T> {
            return f(login)
        }
    }

    data class LoginCredential(val username: String, val password: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return f(username, password)
        }
    }

}