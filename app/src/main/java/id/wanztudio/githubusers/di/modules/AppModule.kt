package id.wanztudio.githubusers.di.modules

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.wanztudio.githubusers.BuildConfig
import id.wanztudio.githubusers.app.App
import id.wanztudio.githubusers.repository.database.AppDatabase
import id.wanztudio.githubusers.repository.database.dao.UserFavoriteDao
import id.wanztudio.githubusers.repository.network.ApiConstants
import id.wanztudio.githubusers.repository.network.ApiServices
import id.wanztudio.githubusers.repository.network.LiveDataCallAdapterFactoryForRetrofit
import id.wanztudio.githubusers.repository.network.http.HttpClientType
import id.wanztudio.githubusers.repository.network.http.OkHttpClientFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
@Module(includes = [
    ActivityModule::class,
    ViewModelModule::class]
)
class AppModule {

    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Singleton
    @Provides
    fun providesResources(application: App): Resources = application.resources

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideApiService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClientFactory.clientByType(HttpClientType.Authorized))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiServices::class.java)
    }

    /**
     * Provides app AppDatabase
     */
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides UserFavoriteDao an object to access CountryEntity table from Database
     */
    @Singleton
    @Provides
    fun provideUserFavoriteDao(db: AppDatabase): UserFavoriteDao{
        return db.userFavoriteDao()
    }
}