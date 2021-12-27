package id.wanztudio.githubusers.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.wanztudio.githubusers.di.base.ViewModelFactory
import id.wanztudio.githubusers.di.base.ViewModelKey
import id.wanztudio.githubusers.ui.detail.UserDetailViewModel
import id.wanztudio.githubusers.ui.list.UserListViewModel
import id.wanztudio.githubusers.ui.list.favorites.UserFavoritesViewModel
import id.wanztudio.githubusers.ui.list.following.UserFollowingViewModel
import id.wanztudio.githubusers.ui.list.folowers.UserFollowersViewModel

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
@Module
abstract class ViewModelModule {

    /**
     * Binds ViewModels factory to provide ViewModels.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(viewModel: UserListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserFollowersViewModel::class)
    abstract fun bindUserFollowersViewModel(viewModel: UserFollowersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserFollowingViewModel::class)
    abstract fun bindUserFollowingViewModel(viewModel: UserFollowingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserFavoritesViewModel::class)
    abstract fun bindUserFavoritesViewModel(viewModel: UserFavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    abstract fun bindUserDetailViewModel(viewModel: UserDetailViewModel): ViewModel
}
