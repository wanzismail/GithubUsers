package id.wanztudio.githubusers.ui.list.following

import androidx.lifecycle.ViewModel
import id.wanztudio.githubusers.repository.repo.GithubRepository
import javax.inject.Inject

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserFollowingViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    fun getFollowingList(username : String)  = repository.getUserFollowingFromApi(username)
}