package com.boodabest.repositories.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boodabest.core.AppExecutors
import com.boodabest.database.User
import com.boodabest.database.UserDao
import com.boodabest.models.Empty
import com.boodabest.models.LoginResponse
import com.boodabest.network.*
import com.boodabest.services.AuthService
import com.boodabest.services.LoginBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val authService: AuthService,
    private val userDao: UserDao
) {

    fun login(username: String, password: String): LiveData<Resource<LoginResponse>> {
        return object : NetworkBoundResourceNoCache<LoginResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<LoginResponse>> {
                return authService.login(LoginBody(username, password))
            }
        }.asLiveData()
    }

    fun fetchMe(login: LoginResponse): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<User>> {
                return authService.profile(login.accessToken)
            }

            override fun loadFromDb() = userDao.get()

            override fun saveCallResult(item: User) {
                val newUser = item.copy(
                    accessToken = login.accessToken,
                    accessTokenExpire = login.accessTokenExpire,
                    refreshToken = login.refreshToken,
                    refreshTokenExpire = login.refreshTokenExpire
                )
                userDao.delete()
                userDao.insert(newUser)
            }

            override fun shouldFetch(data: User?) = true
        }.asLiveData()
    }


    fun me(): LiveData<User> {
        return userDao.get()
    }

    fun logout(): LiveData<Resource<Empty>> {
        val logoutTask = LogoutTask(authService, userDao)
        appExecutors.networkIO().execute(logoutTask)
        return logoutTask.liveData
    }
}

class LogoutTask constructor(
    private val authService: AuthService,
    private val userDao: UserDao
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Empty>>()
    val liveData: LiveData<Resource<Empty>> = _liveData

    override fun run() {
        _liveData.postValue(Resource.loading(Empty()))
        val user = userDao.getResult()
        val token = user?.accessToken ?: ""

        val newValue = try {
            val response = authService.logout(token).execute()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    userDao.delete()
                    Resource.success(Empty())
                }
                is ApiEmptyResponse -> {
                    userDao.delete()
                    Resource.success(Empty())
                }
                is ApiErrorResponse -> {
                    userDao.delete()
                    Resource.error(apiResponse.errorMessage, Empty())
                }
            }

        } catch (e: IOException) {
            Resource.error(e.message!!, Empty())
        }
        _liveData.postValue(newValue)
    }
}