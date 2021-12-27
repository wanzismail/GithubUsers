package id.wanztudio.githubusers.ui.detail

import androidx.lifecycle.ViewModel
import id.wanztudio.githubusers.repository.database.entity.UserFavoriteEntity
import id.wanztudio.githubusers.repository.repo.GithubRepository
import javax.inject.Inject

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserDetailViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    fun getUserDetailByUsername(username :String) = repository.getUserDetailByUsername(username)

    fun getUserDetailFromApi(username :String) = repository.getUserDetailFromApi(username)

    fun addToFavorite(user : UserFavoriteEntity) = repository.addToFavorite(user)

    fun removeFromFavorite(user : UserFavoriteEntity) = repository.removeFromFavorite(user)
}