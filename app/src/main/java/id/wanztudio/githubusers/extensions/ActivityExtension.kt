package id.wanztudio.githubusers.extensions

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import id.wanztudio.githubusers.R
import id.wanztudio.githubusers.helper.NetworkHelper
import timber.log.Timber

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
fun Activity.isConnectedInternet() = NetworkHelper.isConnected

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()
}

fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG)
        .show()
}

fun Activity.showSnackBar(
    containerView: View,
    message: String,
    actionText: String? = null,
    listener: View.OnClickListener? = null,
    length: Int = Snackbar.LENGTH_SHORT
) {
    val snackbar: Snackbar = Snackbar.make(containerView, message, length)
    snackbar.setAction(actionText) {
        // executed when DISMISS is clicked
        Timber.d("Snackbar set action - OnClick.")
    }
    snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.starship))
    snackbar.show()
}

fun Activity.showSnackBar(
    containerView: View,
    message: Int,
    actionText: String? = null,
    listener: View.OnClickListener? = null,
    length: Int = Snackbar.LENGTH_SHORT
) {
    showSnackBar(containerView, getString(message), actionText, listener, length)
}