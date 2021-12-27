package id.wanztudio.githubusers.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.wanztudio.githubusers.ui.detail.UserDetailActivity
import id.wanztudio.githubusers.ui.list.UserListActivity
import id.wanztudio.githubusers.ui.list.favorites.UserFavoritesActivity

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
/**
 * All your Activities participating in DI would be listed here.
 */
@Module(includes = [FragmentModule::class]) // Including Fragment Module Available For Activities
abstract class ActivityModule {

    /**
     * Marking Activities to be available to contributes for Android Injector
     */

    @ContributesAndroidInjector
    abstract fun contributeListActivity(): UserListActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoritesActivity(): UserFavoritesActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): UserDetailActivity
}
