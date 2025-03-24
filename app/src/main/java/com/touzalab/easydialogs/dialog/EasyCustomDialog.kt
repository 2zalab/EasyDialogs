package com.touzalab.easydialogs.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.touzalab.easydialogs.R


/**
 * Custom dialog that allows adding custom content
 */
class EasyCustomDialog(
    context: Context,
    @StyleRes themeResId: Int = R.style.EasyDialog_Custom
) : EasyDialog(context, themeResId) {

    private var title: CharSequence? = null
    private var positiveButtonText: CharSequence? = null
    private var negativeButtonText: CharSequence? = null
    private var customView: View? = null
    private var customLayoutResId: Int = 0

    private var positiveButtonClickListener: (() -> Unit)? = null
    private var negativeButtonClickListener: (() -> Unit)? = null

    private var titleView: TextView? = null
    private var contentContainer: FrameLayout? = null
    private var positiveButton: Button? = null
    private var negativeButton: Button? = null

    /**
     * Set the title of the dialog
     */
    fun setTitle(title: CharSequence?): EasyCustomDialog {
        this.title = title
        titleView?.text = title
        titleView?.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
        return this
    }

    /**
     * Set the title of the dialog from a string resource
     */
    fun setTitle(@StringRes titleResId: Int): EasyCustomDialog {
        return setTitle(context.getString(titleResId))
    }

    /**
     * Set the custom view for the dialog
     */
    fun setView(view: View): EasyCustomDialog {
        this.customView = view
        this.customLayoutResId = 0
        if (contentContainer != null) {
            contentContainer?.removeAllViews()
            contentContainer?.addView(view)
        }
        return this
    }

    /**
     * Set the custom view for the dialog from a layout resource
     */
    fun setView(@LayoutRes layoutResId: Int): EasyCustomDialog {
        this.customLayoutResId = layoutResId
        this.customView = null
        if (contentContainer != null) {
            contentContainer?.removeAllViews()
            val inflatedView = LayoutInflater.from(context).inflate(layoutResId, contentContainer, false)
            contentContainer?.addView(inflatedView)
        }
        return this
    }

    /**
     * Set the text and click listener for the positive button
     */
    fun setPositiveButton(text: CharSequence?, listener: (() -> Unit)? = null): EasyCustomDialog {
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
    fun setPositiveButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): EasyCustomDialog {
        return setPositiveButton(context.getString(textResId), listener)
    }

    /**
     * Set the text and click listener for the negative button
     */
    fun setNegativeButton(text: CharSequence?, listener: (() -> Unit)? = null): EasyCustomDialog {
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
    fun setNegativeButton(@StringRes textResId: Int, listener: (() -> Unit)? = null): EasyCustomDialog {
        return setNegativeButton(context.getString(textResId), listener)
    }

    /**
     * Get the custom view container
     */
    fun getContentContainer(): FrameLayout? {
        return contentContainer
    }

    /**
     * Find a view in the custom view container by id
     */
    fun <T : View> findViewById(id: Int): T? {
        return contentContainer?.findViewById(id)
    }

    /**
     * Show the dialog
     */
    override fun show() {
        val contentView = setContentView(R.layout.easy_custom_dialog)

        // Initialize views
        titleView = contentView.findViewById(R.id.tvTitle)
        contentContainer = contentView.findViewById(R.id.contentContainer)
        positiveButton = contentView.findViewById(R.id.btnPositive)
        negativeButton = contentView.findViewById(R.id.btnNegative)

        // Set title
        setTitle(title)

        // Set custom view
        if (customView != null) {
            setView(customView!!)
        } else if (customLayoutResId != 0) {
            setView(customLayoutResId)
        }

        // Set button text and listeners
        setPositiveButton(positiveButtonText, positiveButtonClickListener)
        setNegativeButton(negativeButtonText, negativeButtonClickListener)

        super.show()
    }

    /**
     * Builder class for EasyCustomDialog
     */
    class Builder(private val context: Context) {
        private val dialog = EasyCustomDialog(context)

        fun setTitle(title: CharSequence?): Builder {
            dialog.setTitle(title)
            return this
        }

        fun setTitle(@StringRes titleResId: Int): Builder {
            dialog.setTitle(titleResId)
            return this
        }

        fun setView(view: View): Builder {
            dialog.setView(view)
            return this
        }

        fun setView(@LayoutRes layoutResId: Int): Builder {
            dialog.setView(layoutResId)
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

        fun setCancelable(cancelable: Boolean): Builder {
            dialog.setCancelable(cancelable)
            return this
        }

        fun setCanceledOnTouchOutside(cancel: Boolean): Builder {
            dialog.setCanceledOnTouchOutside(cancel)
            return this
        }

        fun build(): EasyCustomDialog {
            return dialog
        }

        fun show(): EasyCustomDialog {
            dialog.show()
            return dialog
        }
    }
}