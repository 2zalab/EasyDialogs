package com.touzalab.easydialogs.dialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes

/**
 * Base dialog class for all Easy dialogs
 */
abstract class EasyDialog(
    protected val context: Context,
    @StyleRes private val themeResId: Int = 0
) {
    protected var dialog: Dialog
    protected var cancelable: Boolean = true
    protected var canceledOnTouchOutside: Boolean = true

    init {
        dialog = if (themeResId != 0) {
            Dialog(context, themeResId)
        } else {
            Dialog(context)
        }

        // Request a window with no title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make dialog background transparent by default to use custom background
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    /**
     * Set if the dialog is cancelable
     */
    fun setCancelable(cancelable: Boolean): EasyDialog {
        this.cancelable = cancelable
        dialog.setCancelable(cancelable)
        return this
    }

    /**
     * Set if the dialog can be canceled by touching outside
     */
    fun setCanceledOnTouchOutside(cancel: Boolean): EasyDialog {
        this.canceledOnTouchOutside = cancel
        dialog.setCanceledOnTouchOutside(cancel)
        return this
    }

    /**
     * Set the width of the dialog
     */
    fun setWidth(width: Int): EasyDialog {
        val window = dialog.window
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        return this
    }

    /**
     * Set the height of the dialog
     */
    fun setHeight(height: Int): EasyDialog {
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, height)
        return this
    }

    /**
     * Set the content view of the dialog
     */
    protected fun setContentView(@LayoutRes layoutResId: Int): View {
        val view = LayoutInflater.from(context).inflate(layoutResId, null)
        dialog.setContentView(view)
        return view
    }

    /**
     * Set the content view of the dialog
     */
    protected fun setContentView(view: View) {
        dialog.setContentView(view)
    }

    /**
     * Show the dialog
     */
    open fun show() {
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    /**
     * Dismiss the dialog
     */
    open fun dismiss() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    /**
     * Check if the dialog is showing
     */
    fun isShowing(): Boolean {
        return dialog.isShowing
    }
}