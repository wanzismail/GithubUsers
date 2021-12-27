package id.wanztudio.githubusers.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import id.wanztudio.githubusers.R
import id.wanztudio.githubusers.databinding.UserDetailActivityBinding
import id.wanztudio.githubusers.extensions.*
import id.wanztudio.githubusers.helper.DataMappingHelper
import id.wanztudio.githubusers.repository.model.UserDetail
import id.wanztudio.githubusers.repository.model.UserFavorite
import id.wanztudio.githubusers.ui.base.BaseActivity
import id.wanztudio.githubusers.ui.list.following.UserFollowingFragment
import id.wanztudio.githubusers.ui.list.folowers.UserFollowersFragment
import timber.log.Timber

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserDetailActivity : BaseActivity<UserDetailViewModel>() {

    private lateinit var binding: UserDetailActivityBinding

    private var userDetail: UserDetail? = null
    private var userFavorite: UserFavorite? = null
    private var favoriteActive = false
    private var username: String? = null

    private var menuItem: Menu? = null

    override fun getLayout(): ViewBinding {
        binding = UserDetailActivityBinding.inflate(layoutInflater)
        return binding
    }

    override fun init(bundle: Bundle?) {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        binding.includeContent.tabLayout.setupWithViewPager(binding.includeContent.viewpager)
    }

    override fun initData(bundle: Bundle?) {
        username = intent?.extras?.getString("username")
        favoriteActive = intent?.extras?.getBoolean("is_favorite") ?: false

        binding.includeContent.viewpager.adapter = ViewPagerAdapter(
            this,
            listOf(
                UserFollowersFragment.newInstance(username ?: ""),
                UserFollowingFragment.newInstance(username ?: "")
            ),
            supportFragmentManager
        )

        getDetail(username ?: "")
    }

    override fun initObjectListener(bundle: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        menuItem = menu
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val icon = if (favoriteActive) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
        menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                setFavoriteUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDetail(username: String) {
        getViewModel().getUserDetailByUsername(username)
            .observe(this) { data ->
                handleUserDetailFromDb(DataMappingHelper.mapUserFavoriteEntitiesToModel(data))

                if (data.isEmpty()) {
                    getViewModel().getUserDetailFromApi(username).observe(this) {
                        when {
                            it.status.isLoading() -> {
                            }
                            it.status.isSuccessful() -> {
                                it.data?.let { data ->
                                    handleResultUserDetail(
                                        DataMappingHelper.mapUserDetailResponseToModel(
                                            data
                                        )
                                    )
                                }
                            }
                            it.status.isError() -> {
                                Timber.e(it.throwable)
                                binding.root.let { view ->
                                    showSnackBar(
                                        view,
                                        getString(R.string.failed_fetch_data_caption)
                                    )
                                }
                            }
                        }
                    }
                }
            }
    }

    private fun setFavoriteUser() {
        if (favoriteActive) {
            userFavorite?.let {
                getViewModel().removeFromFavorite(DataMappingHelper.mapUserFavoriteModelToEntity(it))
                showSnackBar(binding.root, R.string.remove_from_favorite)
            }
        } else {
            val userFavorite = userDetail?.let { DataMappingHelper.mapUserDetailToUserFavorite(it) }
            userFavorite?.let {
                getViewModel().addToFavorite(DataMappingHelper.mapUserFavoriteModelToEntity(it))
                showSnackBar(binding.root, R.string.add_to_favorite)
            }
        }
    }

    private fun handleUserDetailFromDb(userFavorite: List<UserFavorite>) {
        if (userFavorite.isEmpty()) {
            favoriteActive = false
            val icon = R.drawable.ic_favorite_outline
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
        } else {
            this.userFavorite = userFavorite.first()
            favoriteActive = true
            val icon = R.drawable.ic_favorite
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)

            binding.apply {
                textName.text = userFavorite.first().name
                toolbarLayout.title = "${username}\'s Profile"
                binding.imageProfile.load(userFavorite.first().avatarUrl)
            }
        }
    }

    private fun handleResultUserDetail(data: UserDetail) {
        userDetail = data
        binding.apply {
            textName.text = data.name
            toolbarLayout.title = "${username}\'s Profile"
            binding.imageProfile.load(userDetail?.avatarUrl)
        }
    }

    companion object {
        fun getIntent(context: Context, username: String, isFavorite : Boolean = false): Intent {
            return Intent(context, UserDetailActivity::class.java).apply {
                putExtra("username", username)
                putExtra("is_favorite", true)
            }
        }
    }
}