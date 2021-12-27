package id.wanztudio.githubusers.ui.list

import id.wanztudio.githubusers.preferences.SettingPreferences
import id.wanztudio.githubusers.repository.repo.GithubRepository
import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserListViewModel @Inject constructor(
    private val repository: GithubRepository,
) : ViewModel() {

    private var preferences : SettingPreferences? = null

    fun getUserList(username : String)  = repository.findUserFromApi(username)

    fun setPreferences(preferences: SettingPreferences){
        this.preferences = preferences
    }

    fun getThemeSettings(): LiveData<Boolean>? {
        return preferences?.getThemeSetting()?.asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences?.saveThemeSetting(isDarkModeActive)
        }
    }
}