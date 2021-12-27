package id.wanztudio.githubusers.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import id.wanztudio.githubusers.app.App

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
object NetworkHelper {

    /**
     * This function to check connection device from all SDK VERSION
     *
     * @return is device connected
     */
    // check if connectivityManager is available
    val isConnected: Boolean
        get() {
            App.instance?.let {
                val connectivityManager =
                    it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                connectivityManager.run {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getNetworkCapabilities(activeNetwork)?.run {
                            return when {
                                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                                hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                                else -> false
                            }
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        return this.activeNetworkInfo?.isConnected ?: false
                    }
                }
            }

            return false
        }
}