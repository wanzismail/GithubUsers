package id.wanztudio.githubusers.helper

import id.wanztudio.githubusers.repository.database.entity.UserFavoriteEntity
import id.wanztudio.githubusers.repository.model.*
import id.wanztudio.githubusers.repository.network.response.UserDetailResponse
import id.wanztudio.githubusers.repository.network.response.UserFollowersResponse
import id.wanztudio.githubusers.repository.network.response.UserFollowingResponse
import id.wanztudio.githubusers.repository.network.response.UserSearchResponse
/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
object DataMappingHelper {

    fun mapUserSearchResponseToModel(data: List<UserSearchResponse>): List<UserResult> {
        val dataList =  data.map {
            UserResult(
                avatarUrl = it.avatarUrl,
                id = it.id,
                username = it.login,
            )
        }
        return dataList
    }

    fun mapUserFollowerResponseToModel(data: List<UserFollowersResponse>): List<UserResult> =
        data.map {
            UserResult(
                avatarUrl = it.avatarUrl,
                id = it.id,
                username = it.login,
            )
        }

    fun mapUserFollowingResponseToModel(data: List<UserFollowingResponse>): List<UserResult> =
        data.map {
            UserResult(
                avatarUrl = it.avatarUrl,
                id = it.id,
                username = it.login,
            )
        }

    fun mapUserDetailResponseToModel(data: UserDetailResponse): UserDetail =
        UserDetail(
            username = data.login.toString(),
            name = data.name,
            avatarUrl = data.avatarUrl,
            followersUrl = data.followersUrl,
            bio = data.bio,
            company = data.company,
            publicRepos = data.publicRepos,
            followingUrl = data.followingUrl,
            followers = data.followers,
            following = data.following,
            location = data.location
        )


    fun mapUserFavoriteEntitiesToModel(data: List<UserFavoriteEntity>): List<UserFavorite> =
        data.map {
            UserFavorite(
                username = it.username,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followersUrl = it.followersUrl,
                bio = it.bio,
                company = it.company,
                publicRepos = it.publicRepos,
                followingUrl = it.followingUrl,
                followers = it.followers,
                following = it.following,
                location = it.location
            )
        }

    fun mapUserFavoriteModelToEntity(data: UserFavorite): UserFavoriteEntity =
        UserFavoriteEntity(
            username = data.username,
            name = data.name,
            avatarUrl = data.avatarUrl,
            followersUrl = data.followersUrl,
            bio = data.bio,
            company = data.company,
            publicRepos = data.publicRepos,
            followingUrl = data.followingUrl,
            followers = data.followers,
            following = data.following,
            location = data.location
        )

    fun mapUserDetailToUserFavorite(it: UserDetail): UserFavorite =
        UserFavorite(
            username = it.username,
            name = it.name,
            avatarUrl = it.avatarUrl,
            followersUrl = it.followersUrl,
            bio = it.bio,
            company = it.company,
            publicRepos = it.publicRepos,
            followingUrl = it.followingUrl,
            followers = it.followers,
            following = it.following,
            location = it.location
        )
}