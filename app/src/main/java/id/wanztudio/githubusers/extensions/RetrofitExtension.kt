package id.wanztudio.githubusers.extensions

import id.wanztudio.githubusers.repository.network.Resource
import retrofit2.Response
import retrofit2.Retrofit

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */

/**
 * Synthetic sugaring to create Retrofit Service.
 */
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

/**
 * Converts Retrofit [Response] to [Resource] which provides state
 * and data to the UI.
 */
fun <ResultType> Response<ResultType>.toResource(): Resource<ResultType> {
    val error = errorBody()?.string() ?: message()
    return when {
        isSuccessful -> {
            val body = body()
            when {
                body != null -> Resource.success(body)
                else -> Resource.error(
                    httpCode = code(),
                    errorBody = error
                )
            }
        }
        else -> Resource.error(
            httpCode = code(),
            errorBody = error
        )
    }
}