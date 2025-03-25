package com.touzalab.easydialogs.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.touzalab.easydialogs.R


/**
 * Class to create and show custom context menus
 */
class EasyContextMenu(
    private val context: Context
) {
    private val menuItems = mutableListOf<EasyMenuItem>()
    private var popupWindow: PopupWindow? = null
    private var dismissListener: (() -> Unit)? = null
    private var width = ViewGroup.LayoutParams.WRAP_CONTENT
    private var height = ViewGroup.LayoutParams.WRAP_CONTENT
    private var elevation = context.resources.getDimensionPixelSize(R.dimen.easyui_menu_elevation ).toFloat()

    /**
     * Add a menu item to the context menu
     */
    fun addItem(
        id: Int,
        title: CharSequence,
        @DrawableRes iconResId: Int = 0,
        listener: ((Int) -> Unit)? = null
    ): EasyContextMenu {
        val icon = if (iconResId != 0) ContextCompat.getDrawable(context, iconResId) else null
        menuItems.add(EasyMenuItem(id, title, icon, listener))
        return this
    }

    /**
     * Add a menu item to the context menu with a title from a string resource
     */
    fun addItem(
        id: Int,
        @StringRes titleResId: Int,
        @DrawableRes iconResId: Int = 0,
        listener: ((Int) -> Unit)? = null
    ): EasyContextMenu {
        return addItem(id, context.getString(titleResId), iconResId, listener)
    }

    /**
     * Add a menu item to the context menu
     */
    fun addItem(
        id: Int,
        title: CharSequence,
        icon: Drawable?,
        listener: ((Int) -> Unit)? = null
    ): EasyContextMenu {
        menuItems.add(EasyMenuItem(id, title, icon, listener))
        return this
    }

    /**
     * Add a menu item to the context menu from a menu resource
     */
    fun addMenuResource(@MenuRes menuResId: Int, listener: ((Int) -> Unit)? = null): EasyContextMenu {
        val popupMenu = android.widget.PopupMenu(context, View(context))
        popupMenu.inflate(menuResId)

        for (i in 0 until popupMenu.menu.size()) {
            val item = popupMenu.menu.getItem(i)
            item.title?.let { addItem(item.itemId, it, item.icon, listener) }
        }

        return this
    }

    /**
     * Set the width of the context menu
     */
    fun setWidth(width: Int): EasyContextMenu {
        this.width = width
        return this
    }

    /**
     * Set the height of the context menu
     */
    fun setHeight(height: Int): EasyContextMenu {
        this.height = height
        return this
    }

    /**
     * Set the elevation of the context menu
     */
    fun setElevation(elevation: Float): EasyContextMenu {
        this.elevation = elevation
        return this
    }

    /**
     * Set a listener for when the context menu is dismissed
     */
    fun setOnDismissListener(listener: () -> Unit): EasyContextMenu {
        this.dismissListener = listener
        return this
    }

    /**
     * Show the context menu at the specified anchor view
     */
    fun show(anchor: View) {
        // Create the content view
        val layoutInflater = LayoutInflater.from(context)
        val contentView = layoutInflater.inflate(R.layout.easy_context_menu, null)

        // Setup recycler view
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.rvMenuItems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MenuAdapter(menuItems) { item ->
            popupWindow?.dismiss()
            item.listener?.invoke(item.id)
        }

        // Create and configure popup window
        popupWindow = PopupWindow(contentView, width, height, true).apply {
            elevation = this@EasyContextMenu.elevation
            setOnDismissListener {
                dismissListener?.invoke()
            }

            // Show the popup window
            showAsDropDown(anchor, 0, 0, Gravity.START)
        }
    }

    /**
     * Show the context menu at the specified location
     */
    fun showAtLocation(parent: View, x: Int, y: Int) {
        // Create the content view
        val layoutInflater = LayoutInflater.from(context)
        val contentView = layoutInflater.inflate(R.layout.easy_context_menu, null)

        // Setup recycler view
        val recyclerView = contentView.findViewById<RecyclerView>(R.id.rvMenuItems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MenuAdapter(menuItems) { item ->
            popupWindow?.dismiss()
            item.listener?.invoke(item.id)
        }

        // Create and configure popup window
        popupWindow = PopupWindow(contentView, width, height, true).apply {
            elevation = this@EasyContextMenu.elevation
            setOnDismissListener {
                dismissListener?.invoke()
            }

            // Show the popup window at location
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y)
        }
    }

    /**
     * Dismiss the context menu if it is showing
     */
    fun dismiss() {
        popupWindow?.dismiss()
        popupWindow = null
    }

    /**
     * Data class to hold menu item information
     */
    data class EasyMenuItem(
        val id: Int,
        val title: CharSequence,
        val icon: Drawable?,
        val listener: ((Int) -> Unit)?
    )

    /**
     * RecyclerView Adapter for the context menu items
     */
    private inner class MenuAdapter(
        private val items: List<EasyMenuItem>,
        private val onItemClick: (EasyMenuItem) -> Unit
    ) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(
                R.layout.easy_menu_item,
                parent,
                false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int = items.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)

            fun bind(item: EasyMenuItem) {
                tvTitle.text = item.title

                if (item.icon != null) {
                    ivIcon.setImageDrawable(item.icon)
                    ivIcon.visibility = View.VISIBLE
                } else {
                    ivIcon.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }
}