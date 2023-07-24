package com.github.matheusadsantos.expandablelistview

// ExpandableListAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView

class ExpandableListAdapter(private val context: Context, private val groupIconKey: Int) :
    BaseExpandableListAdapter() {

    companion object {
        const val ICON_KEY_PARENT_SETTING = 0 // settings (parent)
        const val ICON_KEY_PARENT_CLOSE = 1 // close (parent)

        const val ICON_KEY_CHILD_MAP_LAYERS = 0
        const val ICON_KEY_CHILD_CAR_INFO = 1
        const val ICON_KEY_CHILD_CAR_INFO_SPEED = 2
        const val ICON_KEY_CHILD_RELOAD = 3
        const val ICON_KEY_CHILD_CENTER = 4
        const val ICON_KEY_CHILD_ROUTE = 5
    }

    private var isExpanded = false
    private val groupData = listOf("Parent")
    private val childData =
        listOf("Child 1", "Child 2", "Child 3", "Child 4", "Child 5", "Child 6")

    override fun getGroup(groupPosition: Int): Any {
        return groupData[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_group, null)
        }

        val iconImageView = view?.findViewById<ImageView>(R.id.listGroup)
        val iconResId = if (isExpanded) R.drawable.ic_button_close else R.drawable.ic_button_settings
        iconImageView?.setImageResource(iconResId)
        return view!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return childData.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childData[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_item, null)
        }

        val iconImageView = view?.findViewById<ImageView>(R.id.listItem)
        val iconResId = when (childPosition) {
            ICON_KEY_CHILD_MAP_LAYERS -> R.drawable.ic_button_layers_map
            ICON_KEY_CHILD_CAR_INFO -> R.drawable.ic_button_car_info
            ICON_KEY_CHILD_CAR_INFO_SPEED -> R.drawable.ic_button_info_speed_ignition
            ICON_KEY_CHILD_RELOAD -> R.drawable.ic_button_reload
            ICON_KEY_CHILD_CENTER -> R.drawable.ic_button_map_center
            ICON_KEY_CHILD_ROUTE -> R.drawable.ic_button_route
            else -> 0
        }

        iconImageView?.setImageResource(iconResId)
        return view!!
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return groupData.size
    }

    fun setGroupExpanded(isExpanded: Boolean) {
        this.isExpanded = isExpanded
        notifyDataSetChanged()
    }
}
