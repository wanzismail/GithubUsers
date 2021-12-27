package id.wanztudio.githubusers.repository.network

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
class LiveDataCallAdapterFactoryForRetrofit : CallAdapter.Factory() {
    override fun get(
        returnType: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType =
            getParameterUpperBound(
                0,
                returnType as ParameterizedType
            )
        val rawObservableType =
            getRawType(observableType)
        require(rawObservableType == Resource::class.java) { "type must be a resource" }
        require(observableType is ParameterizedType) { "resource must be parameterized" }
        val bodyType = getParameterUpperBound(
            0,
            observableType
        )
        return LiveDataCallAdapter<Any>(bodyType)
    }
}