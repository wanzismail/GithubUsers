package id.wanztudio.githubusers.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.wanztudio.githubusers.ui.list.UserListFragment
import id.wanztudio.githubusers.ui.list.following.UserFollowingFragment
import id.wanztudio.githubusers.ui.list.folowers.UserFollowersFragment

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
@Module
abstract class FragmentModule {
    /**
     * Injecting Fragments
     */
    @ContributesAndroidInjector
    internal abstract fun contributeUserFragment(): UserListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeFollowersFragment(): UserFollowersFragment

    @ContributesAndroidInjector
    internal abstract fun contributeFollowingFragment(): UserFollowingFragment
}