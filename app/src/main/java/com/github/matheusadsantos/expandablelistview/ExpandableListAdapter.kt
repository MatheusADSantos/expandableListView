package com.github.matheusadsantos.expandablelistview

// ExpandableListAdapter.kt
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout

class ExpandableListAdapter(private val context: Context, private val groupIconKey: Int) :
    BaseExpandableListAdapter() {

    private val groupData = listOf("Pai")
    private val childData =
        listOf("Filho 1", "Filho 2", "Filho 3", "Filho 4", "Filho 5", "Filho 6", "Filho 7")

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
        val iconResId = when (groupIconKey) {
            ICON_KEY_PARENT_1 -> R.drawable.ic_button_settings
            ICON_KEY_PARENT_2 -> R.drawable.ic_button_settings
            ICON_KEY_PARENT_3 -> R.drawable.ic_button_settings
            else -> R.drawable.ic_button_close
        }

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
        val iconResId = when (groupIconKey) {
            ICON_KEY_PARENT_1 -> R.drawable.ic_button_close
            ICON_KEY_PARENT_2 -> R.drawable.ic_button_close
            ICON_KEY_PARENT_3 -> R.drawable.ic_button_close
            ICON_KEY_PARENT_4 -> R.drawable.ic_button_close
            ICON_KEY_PARENT_5 -> R.drawable.ic_button_close
            ICON_KEY_PARENT_6 -> R.drawable.ic_button_close
            ICON_KEY_PARENT_7 -> R.drawable.ic_button_close
            else -> R.drawable.ic_button_close
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

    companion object {
        const val ICON_KEY_PARENT_1 = 1 // settings (parent)
        const val ICON_KEY_PARENT_2 = 2
        const val ICON_KEY_PARENT_3 = 3
        const val ICON_KEY_PARENT_4 = 4
        const val ICON_KEY_PARENT_5 = 5
        const val ICON_KEY_PARENT_6 = 6
        const val ICON_KEY_PARENT_7 = 7
    }
}
