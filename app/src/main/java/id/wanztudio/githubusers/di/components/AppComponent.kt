package id.wanztudio.githubusers.di.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import id.wanztudio.githubusers.app.App
import id.wanztudio.githubusers.di.modules.AppModule
import javax.inject.Singleton

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
/**
 * AndroidInjectionModule::class to support Dagger
 * AppModule::class is loading all modules for app
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}