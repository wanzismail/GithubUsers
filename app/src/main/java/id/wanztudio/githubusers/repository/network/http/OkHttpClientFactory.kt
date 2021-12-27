package id.wanztudio.githubusers.repository.network.http

import id.wanztudio.githubusers.BuildConfig
import id.wanztudio.githubusers.repository.network.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
object OkHttpClientFactory {

    private const val DEFAULT_TIMEOUT: Long = 30

    private fun getDefault(timeout: Long): OkHttpClient.Builder {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            // disable cache
            .cache(null)

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        }
        return httpClientBuilder
    }

    private fun getAuthorized(timeout: Long): OkHttpClient.Builder {
        return getDefault(timeout).addInterceptor(AuthorizationInterceptor())
    }

    fun clientByType(
        type: HttpClientType,
        timeout: Long = DEFAULT_TIMEOUT
    ): OkHttpClient {
        return when (type) {
            is HttpClientType.Default -> getDefault(
                timeout
            ).build()
            is HttpClientType.Authorized -> getAuthorized(
                timeout
            ).build()
        }
    }
}