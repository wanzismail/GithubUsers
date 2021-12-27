package id.wanztudio.githubusers.ui.list.favorites

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import id.wanztudio.githubusers.databinding.UserFavoritesActivityBinding
import id.wanztudio.githubusers.extensions.getViewModel
import id.wanztudio.githubusers.extensions.gone
import id.wanztudio.githubusers.extensions.visible
import id.wanztudio.githubusers.helper.DataMappingHelper
import id.wanztudio.githubusers.repository.model.UserFavorite
import id.wanztudio.githubusers.ui.base.BaseActivity
import id.wanztudio.githubusers.ui.detail.UserDetailActivity

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserFavoritesActivity : BaseActivity<UserFavoritesViewModel>(), UserFavoritesCallback {

    private lateinit var binding: UserFavoritesActivityBinding

    private lateinit var userAdapter: UserFavoritesAdapter

    private var userList: List<UserFavorite> = emptyList()

    override fun getLayout(): ViewBinding {
        binding = UserFavoritesActivityBinding.inflate(layoutInflater)
        return binding
    }

    override fun init(bundle: Bundle?) {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        setUpRecyclerView()
    }

    override fun initData(bundle: Bundle?) {
        getUserList(false)
    }

    override fun initObjectListener(bundle: Bundle?) {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            getUserList(true)
        }
    }

    private fun setUpRecyclerView() {
        userAdapter = UserFavoritesAdapter()
        userAdapter.callback = this

        binding?.recyclerCountry?.apply {
            layoutManager = LinearLayoutManager(this@UserFavoritesActivity)
            adapter = userAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@UserFavoritesActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getUserList(isPullToRefresh: Boolean) {
        binding?.textNoConnection?.gone()
        binding?.textEmptyData?.gone()

        getViewModel().getFavoritesList()
            .observe(this) { data ->

                userList = DataMappingHelper.mapUserFavoriteEntitiesToModel(data)

                if (userList.isEmpty() && !isConnectedNetwork) {
                    binding?.textNoConnection?.visible()
                    binding?.textEmptyData?.gone()
                } else if (userList.isEmpty()) {
                    binding?.textNoConnection?.gone()
                    binding?.textEmptyData?.visible()

                    userAdapter.updateList(emptyList())
                } else {
                    binding?.textEmptyData?.gone()
                    userAdapter.updateList(userList)
                }
            }

    }

    override fun onItemTapped(username: String) {
        startActivity(UserDetailActivity.getIntent(this, username, false))
    }
}