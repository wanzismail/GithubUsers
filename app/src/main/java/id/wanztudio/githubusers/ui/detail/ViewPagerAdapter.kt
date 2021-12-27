package id.wanztudio.githubusers.ui.detail

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import id.wanztudio.githubusers.R
import id.wanztudio.githubusers.ui.list.following.UserFollowingFragment
import id.wanztudio.githubusers.ui.list.folowers.UserFollowersFragment

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class ViewPagerAdapter(
    private val context: Context,
    private val fragments : List<Fragment>,
    private val fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val titles = intArrayOf(R.string.follower_title, R.string.following_title)

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(titles[position])
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int = fragments.size
}