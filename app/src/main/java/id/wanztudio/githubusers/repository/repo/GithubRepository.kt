package id.wanztudio.githubusers.repository.repo

import androidx.lifecycle.LiveData
import id.wanztudio.githubusers.app.AppExecutors
import id.wanztudio.githubusers.repository.database.dao.UserFavoriteDao
import id.wanztudio.githubusers.repository.database.entity.UserFavoriteEntity
import id.wanztudio.githubusers.repository.network.ApiServices
import id.wanztudio.githubusers.repository.network.NetworkBoundResource
import id.wanztudio.githubusers.repository.network.Resource
import id.wanztudio.githubusers.repository.network.response.FollowersResponse
import id.wanztudio.githubusers.repository.network.response.FollowingResponse
import id.wanztudio.githubusers.repository.network.response.SearchResponse
import id.wanztudio.githubusers.repository.network.response.UserDetailResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
@Singleton
class GithubRepository @Inject constructor(
    private val userFavoriteDao: UserFavoriteDao,
    private val apiService : ApiServices,
) {
    fun findUserFromApi(username: String): LiveData<Resource<SearchResponse>> {
        return object : NetworkBoundResource<SearchResponse>() {
            override fun createCall() = apiService.findUser(username)
        }.asLiveData()
    }

    fun getUserFollowersFromApi(username: String): LiveData<Resource<FollowersResponse>> {
        return object : NetworkBoundResource<FollowersResponse>() {
            override fun createCall() = apiService.getFollowerUser(username)
        }.asLiveData()
    }

    fun getUserFollowingFromApi(username: String): LiveData<Resource<FollowingResponse>> {
        return object : NetworkBoundResource<FollowingResponse>() {
            override fun createCall() = apiService.getFollowingUser(username)
        }.asLiveData()
    }

    fun getUserDetailFromApi(username: String): LiveData<Resource<UserDetailResponse>> {
        return object : NetworkBoundResource<UserDetailResponse>() {
            override fun createCall() = apiService.getDetailUser(username)
        }.asLiveData()
    }

    fun getFavorites() = userFavoriteDao.fetchAllUsers()

    fun addToFavorite(user : UserFavoriteEntity) = userFavoriteDao.addUserToFavoriteDB(user)

    fun removeFromFavorite(user : UserFavoriteEntity) = userFavoriteDao.deleteUserFromFavoriteDB(user)

    fun getUserDetailByUsername(username: String) = userFavoriteDao.getFavByUsername(username)

}