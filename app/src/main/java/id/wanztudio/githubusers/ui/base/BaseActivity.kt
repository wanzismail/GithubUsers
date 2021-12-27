package id.wanztudio.githubusers.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import id.wanztudio.githubusers.extensions.isConnectedInternet
import javax.inject.Inject

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
abstract class BaseActivity<T> : AppCompatActivity() , HasSupportFragmentInjector {

    private var containerView = 0

    protected var isConnectedNetwork: Boolean = false

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout().root)

        // prevent using vector drawable from crashes in pre-lollipop
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        isConnectedNetwork = isConnectedInternet()
        val bundle = intent.extras

        init(bundle)
        initData(bundle)
        initObjectListener(bundle)
    }

    /**
     * As a Pattern, define layout resource on onCreateView
     * so you no need override onCreateView
     *
     * @return ViewBinding layout
     */
    abstract fun getLayout(): ViewBinding

    /**
     * Sets fragment container view.
     *
     * @param containerView the container view
     */
    protected fun setFragmentContainerView(containerView: Int) {
        this.containerView = containerView
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

            val fragmentTransaction = supportFragmentManager.beginTransaction()
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

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(containerView, fragment, fragment.tag)

            if (addToBackStack == null) {
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
            }
            fragmentTransaction.commit()
        }
    }

    /**
     * As a Pattern, used for instantiate class or define all module. note: after this method
     * implemented into class child, don't forget call this method inside OnCreate, OnCreateView, etc
     *
     * @param bundle the bundle
     */
    protected abstract fun init(bundle: Bundle?)

    /**
     * As a Pattern, define data / load data at first time cycle . note: after this method implemented
     * into class child, don't forget call this method inside OnCreate, OnCreateView, etc
     *
     * @param bundle the bundle
     */
    protected abstract fun initData(bundle: Bundle?)

    /**
     * As a Pattern, define object listener, exp: onClickListener, onChangedListener, etc. note: after
     * this method implemented into class child, don't forget call this method inside OnCreate,
     * OnCreateView, etc
     *
     * @param bundle the bundle
     */
    protected abstract fun initObjectListener(bundle: Bundle?)

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    /**
     * Clear fragment back stacks.
     */
    fun clearFragmentBackStacks() {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun getCurrentFragmentByFrameId(): Fragment? {
        if (containerView == 0) {
            throw NullPointerException("Container View ID not set")
        }
        return supportFragmentManager.findFragmentById(containerView)
    }

}