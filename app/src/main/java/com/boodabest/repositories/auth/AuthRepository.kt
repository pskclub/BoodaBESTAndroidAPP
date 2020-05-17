package com.boodabest.repositories.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boodabest.core.AppExecutors
import com.boodabest.database.User
import com.boodabest.database.UserDao
import com.boodabest.models.Empty
import com.boodabest.models.LoginResponse
import com.boodabest.models.RepoOptions
import com.boodabest.network.ApiResponse
import com.boodabest.network.NetworkBoundResource
import com.boodabest.network.NetworkBoundResourceNoCache
import com.boodabest.network.Resource
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
                class LoginTask : Runnable {
                    private val _liveData = MutableLiveData<ApiResponse<LoginResponse>>()
                    val liveData: LiveData<ApiResponse<LoginResponse>> = _liveData

                    override fun run() {
                        val login = authService.login(LoginBody(username, password)).execute()
                        if (login.code() == 200) {
                            val profile =
                                authService.profileCall(login.body()!!.accessToken).execute()
                            if (profile.code() == 200) {
                                userDao.delete()
                                userDao.insert(
                                    profile.body()!!.copy(
                                        accessToken = login.body()!!.accessToken,
                                        accessTokenExpire = login.body()!!.accessTokenExpire,
                                        refreshToken = login.body()!!.refreshToken,
                                        refreshTokenExpire = login.body()!!.refreshTokenExpire
                                    )
                                )

                            }
                        }

                        _liveData.postValue(ApiResponse.create(login))
                    }
                }

                val loginTask = LoginTask()
                appExecutors.networkIO().execute(loginTask)
                return loginTask.liveData
            }
        }.asLiveData()
    }


    fun fetchMe(options: RepoOptions = RepoOptions()): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<User>> {
                class FetchMeTask : Runnable {
                    private val _liveData = MutableLiveData<ApiResponse<User>>()
                    val liveData: LiveData<ApiResponse<User>> = _liveData

                    override fun run() {
                        val user = userDao.findResult()
                        val token = user?.accessToken ?: ""
                        if (token.isNotBlank()) {
                            val response =
                                ApiResponse.create(authService.profileCall(token).execute())
                            _liveData.postValue(response)
                        } else {
                            _liveData.postValue(null)
                        }
                    }
                }

                val fetchMeTask = FetchMeTask()
                appExecutors.networkIO().execute(fetchMeTask)
                return fetchMeTask.liveData
            }

            override fun loadFromDb() = userDao.find()

            override fun saveCallResult(item: User) {
                val oldUser = userDao.findResult()
                userDao.update(
                    item.copy(
                        accessToken = oldUser!!.accessToken,
                        accessTokenExpire = oldUser.accessTokenExpire,
                        refreshToken = oldUser.refreshToken,
                        refreshTokenExpire = oldUser.refreshTokenExpire
                    )
                )
            }

            override fun shouldFetch(data: User?) = options.isNetworkOnly || data == null
        }.asLiveData()
    }

    fun me(): LiveData<User> {
        return userDao.find()
    }

    fun logout(): LiveData<Resource<Empty>> {
        class LogoutTask : Runnable {
            private val _liveData = MutableLiveData<Resource<Empty>>()
            val liveData: LiveData<Resource<Empty>> = _liveData

            override fun run() {
                _liveData.postValue(Resource.loading(Empty()))
                val user = userDao.findResult()
                val token = user?.accessToken ?: ""

                val newValue = try {
                    authService.logout(token).execute()
                    userDao.delete()
                    Resource.success(Empty())
                } catch (e: IOException) {
                    Resource.error(e.message!!, Empty())
                }
                _liveData.postValue(newValue)
            }
        }

        val logoutTask = LogoutTask()
        appExecutors.networkIO().execute(logoutTask)
        return logoutTask.liveData
    }
}
