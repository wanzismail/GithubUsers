package id.wanztudio.githubusers.repository.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */

/**
 * A generic class to send loading event up-stream when fetching data
 * only from network.
 *
 */
abstract class NetworkBoundResource<RequestType> @MainThread constructor() {

    /**
     * The final result LiveData
     * MediatorLiveData is a LiveData subclass which may observe
     * other LiveData objects and react on OnChanged events from them.
     *
     * This class correctly propagates its active/inactive states down to source LiveData objects.
     */
    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        // Send loading state to UI
        result.value = Resource.loading()
        fetchFromNetwork()
    }

    /**
     * Fetch the data from network and then send it upstream to UI.
     */
    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        // Make the network call
        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)
            // Dispatch the result
            response?.apply {
                when {
                    // Can be done with if statement if status is successful set this otherwise set error message
                    status.isSuccessful() -> {
                        setValue(this)
                    }
                    else -> {
                        setValue(Resource.error(
                            httpCode = httpCode,
                            errorBody = body,
                            e = throwable
                        ))
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<RequestType>> {
        return result
    }

    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue)
            result.value = newValue
    }

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>
}