package id.wanztudio.githubusers.repository.network.http

import id.wanztudio.githubusers.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .addHeader("Authorization", "token ${BuildConfig.ACCESS_TOKEN}")

        return chain.proceed(requestBuilder.build())
    }
}