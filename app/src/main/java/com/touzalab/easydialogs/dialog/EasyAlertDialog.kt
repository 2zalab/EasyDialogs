package com.touzalab.easydialogs.dialog

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.touzalab.easydialogs.R


/**
 * Alert dialog with customizable title, message, and buttons.
 */
class EasyAlertDialog(
    context: Context,
    @StyleRes themeResId: Int = R.style.EasyDialog_Alert
) : EasyDialog(context, themeResId) {

    private var title: CharSequence? = null
    private var message: CharSequence? = null
    private var positiveButtonText: CharSequence? = null
    private var negativeButtonText: CharSequence? = null
    private var neutralButtonText: CharSequence? = null

    private var positiveButtonClickListener: (() -> Unit)? = null
    private var negativeButtonClickListener: (() -> Unit)? = null
    private var neutralButtonClickListener: (() -> Unit)? = null

    private var titleView: TextView? = null
    private var messageView: TextView? = null
    private var positiveButton: Button? = null
    private var negativeButton: Button? = null
    private var neutralButton: Button? = null

    // Builder methods

    /**
     * Set the title of the dialog
     */
    fun setTitle(title: CharSequence?): EasyAlertDialog {
        this.title = title
        titleView?.text = title
        titleView?.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
        return this
    }

    /**
     * Set the title of the dialog from a string resource
     */
    fun setTitle(@StringRes titleResId: Int): EasyAlertDialog {
        return setTitle(context.getString(titleResId))
    }

    /**
     * Set the message of the dialog
     */
    fun setMessage(message: CharSequence?): EasyAlertDialog {
        this.message = message
        messageView?.text = message
        messageView?.visibility = if (message.isNullOrEmpty()) View.GONE else View.VISIBLE
        return this
    }

    /**
     * Set the message of the dialog from a string resource
     */
    fun setMessage(@StringRes messageResId: Int): EasyAlertDialog {
        return setMessage(context.getString(messageResId))
    }

    /**
     * Set the text and click listener for the positive button
     */
    fun setPositiveButton(text: CharSequence?, listener: (() -> Unit)? = null): EasyAlertDialog {
        this.positiveButtonText = text
        this.positiveButtonClickListener = listener
        positiveButton?.text = text
        positiveButton?.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        positiveButton?.setOnClickListener {
            listener?.invoke()
            dismiss()
        }
        return this
    }

    /**
     * Set the text and click listener for the positive button from a string resource
     */
    fun setPositiveButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): EasyAlertDialog {
        return setPositiveButton(context.getString(textResId), listener)
    }

    /**
     * Set the text and click listener for the negative button
     */
    fun setNegativeButton(text: CharSequence?, listener: (() -> Unit)? = null): EasyAlertDialog {
        this.negativeButtonText = text
        this.negativeButtonClickListener = listener
        negativeButton?.text = text
        negativeButton?.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        negativeButton?.setOnClickListener {
            listener?.invoke()
            dismiss()
        }
        return this
    }

    /**
     * Set the text and click listener for the negative button from a string resource
     */
    fun setNegativeButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): EasyAlertDialog {
        return setNegativeButton(context.getString(textResId), listener)
    }

    /**
     * Set the text and click listener for the neutral button
     */
    fun setNeutralButton(text: CharSequence?, listener: (() -> Unit)? = null): EasyAlertDialog {
        this.neutralButtonText = text
        this.neutralButtonClickListener = listener
        neutralButton?.text = text
        neutralButton?.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        neutralButton?.setOnClickListener {
            listener?.invoke()
            dismiss()
        }
        return this
    }

    /**
     * Set the text and click listener for the neutral button from a string resource
     */
    fun setNeutralButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): EasyAlertDialog {
        return setNeutralButton(context.getString(textResId), listener)
    }

    /**
     * Set the title text appearance
     */
    fun setTitleTextAppearance(@StyleRes resId: Int): EasyAlertDialog {
        titleView?.setTextAppearance(resId)
        return this
    }

    /**
     * Set the message text appearance
     */
    fun setMessageTextAppearance(@StyleRes resId: Int): EasyAlertDialog {
        messageView?.setTextAppearance(resId)
        return this
    }

    /**
     * Set the title text style
     */
    fun setTitleTextStyle(style: Int): EasyAlertDialog {
        titleView?.setTypeface(titleView?.typeface, style)
        return this
    }

    /**
     * Show the dialog
     */
    override fun show() {
        val contentView = setContentView(R.layout.easy_alert_dialog)

        // Initialize views
        titleView = contentView.findViewById(R.id.tvTitle)
        messageView = contentView.findViewById(R.id.tvMessage)
        positiveButton = contentView.findViewById(R.id.btnPositive)
        negativeButton = contentView.findViewById(R.id.btnNegative)
        neutralButton = contentView.findViewById(R.id.btnNeutral)

        // Set title and message
        setTitle(title)
        setMessage(message)

        // Set button text and listeners
        setPositiveButton(positiveButtonText, positiveButtonClickListener)
        setNegativeButton(negativeButtonText, negativeButtonClickListener)
        setNeutralButton(neutralButtonText, neutralButtonClickListener)

        super.show()
    }

    /**
     * Builder class for EasyAlertDialog
     */
    class Builder(private val context: Context) {
        private val dialog = EasyAlertDialog(context)

        fun setTitle(title: CharSequence?): Builder {
            dialog.setTitle(title)
            return this
        }

        fun setTitle(@StringRes titleResId: Int): Builder {
            dialog.setTitle(titleResId)
            return this
        }

        fun setMessage(message: CharSequence?): Builder {
            dialog.setMessage(message)
            return this
        }

        fun setMessage(@StringRes messageResId: Int): Builder {
            dialog.setMessage(messageResId)
            return this
        }

        fun setPositiveButton(text: CharSequence?, listener: (() -> Unit)? = null): Builder {
            dialog.setPositiveButton(text, listener)
            return this
        }

        fun setPositiveButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): Builder {
            dialog.setPositiveButton(textResId, listener)
            return this
        }

        fun setNegativeButton(text: CharSequence?, listener: (() -> Unit)? = null): Builder {
            dialog.setNegativeButton(text, listener)
            return this
        }

        fun setNegativeButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): Builder {
            dialog.setNegativeButton(textResId, listener)
            return this
        }

        fun setNeutralButton(text: CharSequence?, listener: (() -> Unit)? = null): Builder {
            dialog.setNeutralButton(text, listener)
            return this
        }

        fun setNeutralButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): Builder {
            dialog.setNeutralButton(textResId, listener)
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            dialog.setCancelable(cancelable)
            return this
        }

        fun setCanceledOnTouchOutside(cancel: Boolean): Builder {
            dialog.setCanceledOnTouchOutside(cancel)
            return this
        }

        fun build(): EasyAlertDialog {
            return dialog
        }

        fun show(): EasyAlertDialog {
            dialog.show()
            return dialog
        }
    }
}