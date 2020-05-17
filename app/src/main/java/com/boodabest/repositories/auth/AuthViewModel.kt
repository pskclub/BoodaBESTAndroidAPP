package com.boodabest.repositories.auth

import androidx.lifecycle.*
import com.boodabest.database.User
import com.boodabest.models.LoginResponse
import com.boodabest.models.RepoOptions
import com.boodabest.network.Resource
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _isAuth = authRepository.me().map {
        it != null
    }

    private val _loginCredential: MutableLiveData<LoginCredential> = MutableLiveData()
    private val _fetchMe: MutableLiveData<FetchMe> = MutableLiveData()

    val isAuth: LiveData<Boolean>
        get() = _isAuth

    val me: LiveData<Resource<User>> = _fetchMe.switchMap { input ->
        input.ifExists { options ->
            authRepository.fetchMe(options)
        }
    }

    val loginItem: LiveData<Resource<LoginResponse>> = _loginCredential.switchMap { input ->
        input.ifExists { username, password ->
            authRepository.login(username, password)
        }
    }

    fun logout() {
        authRepository.logout()
    }

    fun fetchMe() {
        val update = FetchMe(RepoOptions(isNetworkOnly = true))
        _fetchMe.value = update
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

    data class FetchMe(val options: RepoOptions = RepoOptions()) {
        fun <T> ifExists(f: (RepoOptions) -> LiveData<T>): LiveData<T> {
            return f(options)
        }
    }
}