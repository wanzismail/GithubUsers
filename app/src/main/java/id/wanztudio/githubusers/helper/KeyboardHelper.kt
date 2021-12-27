package id.wanztudio.githubusers.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author Ridwan Ismail  (iwanz@pm.me)
 * @version Source, v 0.1 26/12/2021 by Ridwan Ismail
 */
object KeyboardHelper {

    private const val INPUT_FROM_SOFT_KEYBOARD = "input_from_soft_keyboard"
    private const val INPUT_FROM_BARCODE = "input_from_barcode"

    /**
     * Hide keyboard.
     *
     * @param context the context
     * @param view the view
     */
    fun hideKeyboard(context: Context?, view: View?) {
        if (context != null && view != null) {
            val keyboard = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Show keyboard.
     *
     * @param context the context
     * @param view the view
     */
    fun showKeyboard(context: Context, view: View) {
        val inputMethodManager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }


    /**
     * Sets parent to implement auto dismiss keyboard when touch outside of EditText.
     *
     * @param view the view
     * @param context the context
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setupAutoDismissKeyboard(view: View?, context: Context) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view?.setOnTouchListener { v, event ->
                hideKeyboard(context, view)
                false
            }
        }
        //If a layout container, iterate over children
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupAutoDismissKeyboard(innerView, context)
            }
        }
    }

    fun isInputFromHardKeyboard(configuration: Configuration): Boolean {
        var inputType = INPUT_FROM_SOFT_KEYBOARD
        if (configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            //A hardware keyboard is being connected
            inputType = INPUT_FROM_BARCODE
        } else if (configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            //A hardware keyboard is being disconnected
            inputType = INPUT_FROM_SOFT_KEYBOARD
        }

        return inputType == INPUT_FROM_BARCODE
    }

}