package id.wanztudio.githubusers.ui.list

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.wanztudio.githubusers.databinding.UserListFragmentBinding
import id.wanztudio.githubusers.di.base.Injectable
import id.wanztudio.githubusers.extensions.getViewModel
import id.wanztudio.githubusers.extensions.gone
import id.wanztudio.githubusers.extensions.visible
import id.wanztudio.githubusers.helper.DataMappingHelper
import id.wanztudio.githubusers.helper.KeyboardHelper
import id.wanztudio.githubusers.repository.model.UserResult
import id.wanztudio.githubusers.ui.base.BaseFragment
import id.wanztudio.githubusers.ui.detail.UserDetailActivity
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class UserListFragment : BaseFragment<UserDetailActivity>(), Injectable,
    UserListCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val userViewModel: UserListViewModel by lazy {
        getViewModel(viewModelFactory)
    }

    private var binding: UserListFragmentBinding? = null

    private lateinit var userAdapter: UserListAdapter

    private var userList: List<UserResult> = emptyList()
    private var username : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun init(view: View, bundle: Bundle?) {
        binding?.textInputLayoutSearch?.visible()

        setUpRecyclerView()
    }

    override fun initData(view: View, bundle: Bundle?) {
        getUserList(false)
    }

    override fun initObjectListener(view: View, bundle: Bundle?) {
        userAdapter.callback = this
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            getUserList(true)
        }

        binding?.editTextSearch?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardHelper.hideKeyboard(v.context, v)
                username = binding?.editTextSearch?.text.toString()
                getUserList(false)
                return@setOnEditorActionListener true
            } else {
                return@setOnEditorActionListener false
            }
        }
    }

    private fun setUpRecyclerView() {
        userAdapter = UserListAdapter()
        binding?.recyclerCountry?.apply {
            layoutManager = LinearLayoutManager(getParentActivity())
            adapter = userAdapter
            addItemDecoration(
                DividerItemDecoration(
                    getParentActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getUserList(isPullToRefresh: Boolean) {
        binding?.textNoConnection?.gone()
        binding?.textEmptyData?.gone()

        userViewModel.getUserList(username)
            .observe(this) {
                when {
                    it.status.isLoading() -> {
                        if (isPullToRefresh) {
                            binding?.progressbarLoading?.gone()
                        } else binding?.progressbarLoading?.visible()
                    }
                    it.status.isSuccessful() -> {
                        hideProgress()

                        it.data?.let { data ->
                           userList = DataMappingHelper.mapUserSearchResponseToModel(data.users ?: emptyList())

                            if (userList.isEmpty() && !isConnectedNetwork) {
                                binding?.textNoConnection?.visible()
                                binding?.textEmptyData?.gone()
                            } else {
                                binding?.textEmptyData?.gone()
                                userAdapter.updateList(userList)
                            }
                        }
                    }
                    it.status.isError() -> {
                        hideProgress()

                        Timber.e(it.throwable)

                        binding?.textNoConnection?.gone()
                        binding?.textEmptyData?.visible()

                        userList = emptyList()
                        userAdapter.updateList(userList)
                    }
                }
            }
    }

    private fun hideProgress() {
        binding?.progressbarLoading?.gone()
        binding?.swipeRefreshLayout?.isRefreshing = false
    }

    override fun onItemTapped(username: String) {
        startActivity(UserDetailActivity.getIntent(requireContext(), username))
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}