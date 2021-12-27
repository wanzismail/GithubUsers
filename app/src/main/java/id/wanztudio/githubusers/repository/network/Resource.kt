package id.wanztudio.githubusers.repository.network

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<ResultType>(
    var status: Status,
    var data: ResultType? = null,
    var httpCode: Int? = null,
    var body: String? = null,
    var throwable: Throwable? = null
) {

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         * Returning object of Resource(Status.SUCCESS, data, null)  last value is null so passing it optionally
         */
        fun <ResultType> success(data: ResultType): Resource<ResultType> =
            Resource(
                Status.SUCCESS,
                data
            )

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         * Returning object of Resource(Status.SUCCESS, null, null) last two values are null so passing them optionally
         */
        fun <ResultType> loading(): Resource<ResultType> =
            Resource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status, [message] and [throwable].
         * Returning object of Resource(Status.ERROR, errorMessage = message)
         */
        fun <ResultType> error(
            httpCode: Int? = null,
            errorBody: String? = null,
            e: Throwable? = null
        ): Resource<ResultType> =
            Resource(
                Status.ERROR,
                httpCode = httpCode,
                body = errorBody,
                throwable = e
            )

    }
}