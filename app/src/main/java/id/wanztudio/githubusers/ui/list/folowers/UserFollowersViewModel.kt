package id.wanztudio.githubusers.ui.list.folowers

import androidx.lifecycle.ViewModel
import id.wanztudio.githubusers.repository.repo.GithubRepository
import javax.inject.Inject

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserFollowersViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    fun getFollowersList(username : String)  = repository.getUserFollowersFromApi(username)
}