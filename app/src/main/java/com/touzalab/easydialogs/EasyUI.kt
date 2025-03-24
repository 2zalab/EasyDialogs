package com.touzalab.easydialogs

import android.content.Context
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.annotation.StyleRes
import com.touzalab.easydialogs.dialog.EasyAlertDialog
import com.touzalab.easydialogs.dialog.EasyCustomDialog
import com.touzalab.easyandroidui.menu.EasyContextMenu
import com.touzalab.easyandroidui.menu.EasyDropdownMenu
import com.touzalab.easyandroidui.menu.EasyMenu
import com.google.android.material.textfield.TextInputLayout

/**
 * Main entry point for the EasyAndroidUI library
 */
object EasyUI {

    /**
     * Create an alert dialog
     *
     * @param context The context
     * @param themeResId Optional theme resource ID for the dialog
     * @return An EasyAlertDialog instance
     */
    @JvmStatic
    fun createAlertDialog(
        context: Context,
        @StyleRes themeResId: Int = R.style.EasyDialog_Alert
    ): EasyAlertDialog {
        return EasyAlertDialog(context, themeResId)
    }

    /**
     * Create an alert dialog using the builder pattern
     *
     * @param context The context
     * @return An EasyAlertDialog.Builder instance
     */
    @JvmStatic
    fun alertDialog(context: Context): EasyAlertDialog.Builder {
        return EasyAlertDialog.Builder(context)
    }

    /**
     * Create a custom dialog
     *
     * @param context The context
     * @param themeResId Optional theme resource ID for the dialog
     * @return An EasyCustomDialog instance
     */
    @JvmStatic
    fun createCustomDialog(
        context: Context,
        @StyleRes themeResId: Int = R.style.EasyDialog_Custom
    ): EasyCustomDialog {
        return EasyCustomDialog(context, themeResId)
    }

    /**
     * Create a custom dialog using the builder pattern
     *
     * @param context The context
     * @return An EasyCustomDialog.Builder instance
     */
    @JvmStatic
    fun customDialog(context: Context): EasyCustomDialog.Builder {
        return EasyCustomDialog.Builder(context)
    }

    /**
     * Create a dropdown menu
     *
     * @param context The context
     * @param anchor The view to anchor the menu to
     * @return An EasyMenu instance
     */
    @JvmStatic
    fun createMenu(context: Context, anchor: View): EasyMenu {
        return EasyMenu(context, anchor)
    }

    /**
     * Create a context menu
     *
     * @param context The context
     * @return An EasyContextMenu instance
     */
    @JvmStatic
    fun createContextMenu(context: Context): EasyContextMenu {
        return EasyContextMenu(context)
    }

    /**
     * Create a dropdown menu using TextInputLayout and AutoCompleteTextView
     *
     * @param context The context
     * @param textInputLayout The TextInputLayout
     * @param autoCompleteTextView The AutoCompleteTextView
     * @return An EasyDropdownMenu instance
     */
    @JvmStatic
    fun <T> createDropdownMenu(
        context: Context,
        textInputLayout: TextInputLayout,
        autoCompleteTextView: AutoCompleteTextView
    ): EasyDropdownMenu<T> {
        return EasyDropdownMenu(context, textInputLayout, autoCompleteTextView)
    }

    /**
     * Create a dropdown menu from an existing TextInputLayout
     *
     * @param context The context
     * @param textInputLayout The TextInputLayout containing an AutoCompleteTextView
     * @return An EasyDropdownMenu instance
     */
    @JvmStatic
    fun <T> createDropdownMenu(
        context: Context,
        textInputLayout: TextInputLayout
    ): EasyDropdownMenu<T> {
        return EasyDropdownMenu.from(context, textInputLayout)
    }
}