package com.touzalab.easyandroidui.menu

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.touzalab.easyandroidui.R


/**
 * Class to create and show dropdown menus
 */
class EasyMenu(
    private val context: Context,
    private val anchor: View
) {
    private val menuItems = mutableListOf<EasyMenuItem>()
    private var menuStyle: Int = R.style.EasyMenu

    /**
     * Add a menu item to the menu
     */
    fun addItem(
        id: Int,
        title: CharSequence,
        @DrawableRes iconResId: Int = 0,
        listener: ((Int) -> Unit)? = null
    ): EasyMenu {
        menuItems.add(EasyMenuItem(id, title, iconResId, listener))
        return this
    }

    /**
     * Add a menu item to the menu with a title from a string resource
     */
    fun addItem(
        id: Int,
        @StringRes titleResId: Int,
        @DrawableRes iconResId: Int = 0,
        listener: ((Int) -> Unit)? = null
    ): EasyMenu {
        return addItem(id, context.getString(titleResId), iconResId, listener)
    }

    /**
     * Add menu items from a menu resource
     */
    fun addMenuResource(@MenuRes menuResId: Int, listener: ((Int) -> Unit)? = null): EasyMenu {
        val popupMenu = PopupMenu(context, View(context))
        popupMenu.inflate(menuResId)

        for (i in 0 until popupMenu.menu.size()) {
            val item = popupMenu.menu.getItem(i)
            item.title?.let { addItem(item.itemId, it, item.iconResId ?: 0, listener) }
        }

        return this
    }

    /**
     * Set the menu style
     */
    fun setMenuStyle(style: Int): EasyMenu {
        this.menuStyle = style
        return this
    }

    /**
     * Show the menu
     */
    fun show() {
        val contextWrapper = ContextThemeWrapper(context, menuStyle)
        val popupMenu = PopupMenu(contextWrapper, anchor)

        // Add items to the menu
        for (item in menuItems) {
            val menuItem = popupMenu.menu.add(Menu.NONE, item.id, Menu.NONE, item.title)
            if (item.iconResId != 0) {
                menuItem.setIcon(item.iconResId)
            }
        }

        // Set item click listener
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val selectedItem = menuItems.find { it.id == menuItem.itemId }
            selectedItem?.listener?.invoke(menuItem.itemId)
            true
        }

        popupMenu.show()
    }

    /**
     * Data class to hold menu item information
     */
    data class EasyMenuItem(
        val id: Int,
        val title: CharSequence,
        @DrawableRes val iconResId: Int,
        val listener: ((Int) -> Unit)?
    )

    /**
     * Extension property to get the icon resource ID from a MenuItem
     */
    private val MenuItem.iconResId: Int?
        get() = try {
            val field = MenuItem::class.java.getDeclaredField("mIconResId")
            field.isAccessible = true
            field.getInt(this)
        } catch (e: Exception) {
            null
        }
}