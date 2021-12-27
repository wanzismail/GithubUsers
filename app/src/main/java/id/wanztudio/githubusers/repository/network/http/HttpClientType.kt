package id.wanztudio.githubusers.repository.network.http

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
sealed class HttpClientType {
    object Default : HttpClientType()
    object Authorized : HttpClientType()
}