package id.wanztudio.githubusers.repository.network

import androidx.lifecycle.LiveData
import id.wanztudio.githubusers.repository.network.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */

/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    @GET("search/users?")
    fun findUser(
        @Query("q") q : String
    ) : LiveData<Resource<SearchResponse>>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : LiveData<Resource<UserDetailResponse>>

    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username: String
    ) : LiveData<Resource<FollowersResponse>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ) : LiveData<Resource<FollowingResponse>>
}