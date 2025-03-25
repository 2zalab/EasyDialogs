package com.touzalab.easydialogs.menu
import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.ArrayRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.textfield.TextInputLayout


/**
 * Class to create and setup dropdown menus using Material Design's TextInputLayout + AutoCompleteTextView
 */
class EasyDropdownMenu<T>(
    private val context: Context,
    private val textInputLayout: TextInputLayout,
    private val autoCompleteTextView: AutoCompleteTextView
) {
    private var items = listOf<T>()
    private var selectedItem: T? = null
    private var itemClickListener: ((T) -> Unit)? = null

    init {
        // Disable keyboard popup for dropdown
        autoCompleteTextView.inputType = 0

        // Setup basic dropdown behavior
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            this.selectedItem = selectedItem
            itemClickListener?.invoke(selectedItem)
        }
    }

    /**
     * Set the items for the dropdown menu
     */
    fun setItems(items: List<T>): EasyDropdownMenu<T> {
        this.items = items
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line,
            items
        )
        autoCompleteTextView.setAdapter(adapter)
        return this
    }

    /**
     * Set the items for the dropdown menu from a string array resource
     */
    fun setItems(@ArrayRes itemsArrayResId: Int): EasyDropdownMenu<String> {
        @Suppress("UNCHECKED_CAST")
        val stringItems = context.resources.getStringArray(itemsArrayResId).toList() as List<T>
        return setItems(stringItems) as EasyDropdownMenu<String>
    }

    /**
     * Set the custom adapter for the dropdown menu
     */
    fun <A : ArrayAdapter<T>> setAdapter(adapter: A): EasyDropdownMenu<T> {
        autoCompleteTextView.setAdapter(adapter)
        return this
    }

    /**
     * Set the custom dropdown resource layout
     */
    fun setDropDownViewResource(@LayoutRes resId: Int): EasyDropdownMenu<T> {
        (autoCompleteTextView.adapter as? ArrayAdapter<*>)?.setDropDownViewResource(resId)
        return this
    }

    /**
     * Set an item selected listener
     */
    fun setOnItemSelectedListener(listener: (T) -> Unit): EasyDropdownMenu<T> {
        this.itemClickListener = listener
        return this
    }

    /**
     * Select an item programmatically
     */
    fun setSelectedItem(item: T): EasyDropdownMenu<T> {
        if (items.contains(item)) {
            selectedItem = item
            autoCompleteTextView.setText(item.toString(), false)
            itemClickListener?.invoke(item)
        }
        return this
    }

    /**
     * Select an item by position programmatically
     */
    fun setSelectedItemPosition(position: Int): EasyDropdownMenu<T> {
        if (position in items.indices) {
            val item = items[position]
            setSelectedItem(item)
        }
        return this
    }

    /**
     * Get the currently selected item
     */
    fun getSelectedItem(): T? {
        return selectedItem
    }

    /**
     * Set the dropdown menu hint
     */
    fun setHint(hint: CharSequence): EasyDropdownMenu<T> {
        textInputLayout.hint = hint
        return this
    }

    /**
     * Set the dropdown text appearance
     */
    fun setTextAppearance(@StyleRes resId: Int): EasyDropdownMenu<T> {
        autoCompleteTextView.setTextAppearance(resId)
        return this
    }

    /**
     * Set whether the dropdown is enabled
     */
    fun setEnabled(enabled: Boolean): EasyDropdownMenu<T> {
        textInputLayout.isEnabled = enabled
        autoCompleteTextView.isEnabled = enabled
        return this
    }

    /**
     * Show the dropdown menu programmatically
     */
    fun showDropDown() {
        autoCompleteTextView.showDropDown()
    }

    /**
     * Dismiss the dropdown menu programmatically
     */
    fun dismissDropDown() {
        autoCompleteTextView.dismissDropDown()
    }

    companion object {
        /**
         * Create an EasyDropdownMenu instance from a TextInputLayout that contains
         * an AutoCompleteTextView
         */
        fun <T> from(
            context: Context,
            textInputLayout: TextInputLayout
        ): EasyDropdownMenu<T> {
            val autoCompleteTextView = textInputLayout.editText as? AutoCompleteTextView
                ?: throw IllegalStateException("TextInputLayout must contain an AutoCompleteTextView")

            return EasyDropdownMenu(context, textInputLayout, autoCompleteTextView)
        }

        /**
         * Create an EasyDropdownMenu instance from a View that has a TextInputLayout with ID
         * that contains an AutoCompleteTextView
         */
        fun <T> from(
            context: Context,
            container: View,
            textInputLayoutId: Int
        ): EasyDropdownMenu<T> {
            val textInputLayout = container.findViewById<TextInputLayout>(textInputLayoutId)
                ?: throw IllegalStateException("View with ID $textInputLayoutId not found or not a TextInputLayout")

            return from(context, textInputLayout)
        }
    }
}