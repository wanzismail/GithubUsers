package id.wanztudio.githubusers.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import id.wanztudio.githubusers.extensions.isConnectedInternet

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<T> : Fragment() {

    private var containerView = 0

    open fun getParentActivity(): T {
        return activity as T
    }

    open fun getMyParentFragment(): T {
        return parentFragment as T
    }

    protected var isConnectedNetwork: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isConnectedNetwork = activity?.isConnectedInternet() ?: false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: Bundle = arguments ?: Bundle()

        init(view, bundle)
        initData(view, bundle)
        initObjectListener(view, bundle)
    }

    /**
     * As a Pattern, used for instantiate class or define all module. note: after this method
     * implemented into class child, don't forget call this method inside OnCreate, OnCreateView, etc
     *
     * @param view as passing data view
     * @param bundle as passing data bundle
     */
    abstract fun init(view: View, bundle: Bundle?)

    /**
     * As a Pattern, define data / load data at first time cycle . note: after this method implemented
     * into class child, don't forget call this method inside OnCreate, OnCreateView, etc
     *
     * @param view as passing data view
     * @param bundle as passing data bundle
     */
    abstract fun initData(view: View, bundle: Bundle?)

    /**
     * As a Pattern, define object listener, exp: onClickListener, onChangedListener, etc. note: after
     * this method implemented into class child, don't forget call this method inside OnCreate,
     * OnCreateView, etc.
     *
     * @param view as passing data view
     * @param bundle as passing data bundle
     */
    abstract fun initObjectListener(view: View, bundle: Bundle?)

    /**
     * Sets fragment container view.
     *
     * @param containerView the container view
     */
    protected fun setFragmentContainerView(containerView: Int?) {
        this.containerView = containerView ?: 0
    }

    /**
     * Replace fragment.
     *
     * @param fragment the fragment
     * @param bundle the bundle
     * @param addToBackStack the add to back stack
     */
    fun replaceFragment(fragment: Fragment, bundle: Bundle? = null, addToBackStack: Boolean = false) {

        if (containerView != 0) {
            // set bundle if exits
            bundle?.let {
                fragment.arguments = bundle
            }

            val fragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace(containerView, fragment, fragment.tag)

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
            }

            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    /**
     * Add fragment.
     *
     * @param fragment the fragment
     * @param bundle the bundle
     * @param addToBackStack the add to back stack
     */
    fun addFragment(fragment: Fragment, bundle: Bundle? = null, addToBackStack: Boolean? = null) {

        if (containerView != 0) {
            // set bundle if exits
            bundle?.let {
                fragment.arguments?.putAll(bundle) ?: bundle
            }

            val fragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.add(containerView, fragment, fragment.tag)

            if (addToBackStack == null) {
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
            }
            fragmentTransaction.commit()
        }
    }

    /**
     * Clear fragment back stacks.
     */
    fun clearFragmentBackStacks() {
        childFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun getCurrentFragmentByFrameId(): Fragment? {
        if (containerView == 0) {
            throw NullPointerException("Container View ID not set")
        }
        return childFragmentManager.findFragmentById(containerView)
    }
}