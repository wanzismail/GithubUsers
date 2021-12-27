package id.wanztudio.githubusers.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.viewbinding.ViewBinding
import id.wanztudio.githubusers.R
import id.wanztudio.githubusers.databinding.UserListActivityBinding
import id.wanztudio.githubusers.extensions.getViewModel
import id.wanztudio.githubusers.preferences.SettingPreferences
import id.wanztudio.githubusers.ui.base.BaseActivity
import id.wanztudio.githubusers.ui.list.favorites.UserFavoritesActivity

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserListActivity : BaseActivity<UserListViewModel>() {

    private lateinit var binding: UserListActivityBinding

    private var darkMode : Boolean = false

    override fun getLayout(): ViewBinding {
        binding = UserListActivityBinding.inflate(layoutInflater)
        return binding
    }

    override fun init(bundle: Bundle?) {
        setSupportActionBar(binding?.toolbar)
        setFragmentContainerView(R.id.container)
        replaceFragment(UserListFragment())
    }

    override fun initData(bundle: Bundle?) {
        val prefs = SettingPreferences.getInstance(dataStore)
        getViewModel().setPreferences(prefs)
    }

    override fun initObjectListener(bundle: Bundle?) {
        getViewModel().getThemeSettings()?.observe(this,
            { isDarkModeActive: Boolean ->
                darkMode = if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    false
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val icon = if(darkMode) R.drawable.ic_dark_mode else R.drawable.ic_light_mode
        menu?.getItem(1)?.icon = ContextCompat.getDrawable(this, icon)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, UserFavoritesActivity::class.java))
            }
            R.id.menu_dark_mode -> {
                getViewModel().saveThemeSetting(!darkMode)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}