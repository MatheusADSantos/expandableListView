package com.github.matheusadsantos.expandablelistview

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.core.content.ContextCompat
import com.github.matheusadsantos.expandablelistview.ChildButtonInfo.ChildButtonInfo
import com.github.matheusadsantos.expandablelistview.databinding.ListGroupBinding
import com.github.matheusadsantos.expandablelistview.databinding.ListItemBinding

class ExpandableListAdapter(private val context: Context) : BaseExpandableListAdapter() {

    companion object {
        const val ICON_KEY_CHILD_MAP_LAYERS = 0
        const val ICON_KEY_CHILD_TRACK_INFO = 1
        const val ICON_KEY_CHILD_TRACK_INFO_SPEED = 2
        const val ICON_KEY_CHILD_MAP_RELOAD = 3
        const val ICON_KEY_CHILD_MAP_CENTER = 4
        const val ICON_KEY_CHILD_TRACK_ROUTE = 5
    }

    private lateinit var bindingListItem: ListItemBinding
    private var isExpandedChild = mutableMapOf<Int, Boolean>()
    private var isExpandedGroup = false

    private val groupData = listOf("Parent")
    val childData = listOf(
        "mapLayears",
        "trackInfo",
        "trackInfoSpeedIg",
        "mapReload",
        "mapCenter",
        "trackRoute"
    )

    fun getChildButtonInfo(childPosition: Int): ChildButtonInfo {
        return when (childPosition) {
            ICON_KEY_CHILD_MAP_LAYERS -> ChildButtonInfo(
                listOf("Padrão", "Satélite", "Trânsito"),
                listOf(
                    R.drawable.ic_map_layer_default,
                    R.drawable.ic_map_layer_satellite,
                    R.drawable.ic_map_layer_traffic
                )
            )

            ICON_KEY_CHILD_TRACK_INFO -> ChildButtonInfo(
                listOf("Placa", "Descrição", "test..."),
                listOf(
                    R.drawable.ic_track_info_plate,
                    R.drawable.ic_track_info_description,
                    R.drawable.ic_track_info_description
                )
            )

            ICON_KEY_CHILD_TRACK_INFO_SPEED -> ChildButtonInfo(
                listOf("Velocidade", "Ignição", "test..."),
                listOf(
                    R.drawable.ic_track_info_speed,
                    R.drawable.ic_track_info_ignition,
                    R.drawable.ic_track_info_description
                )
            )

            else -> ChildButtonInfo(emptyList(), emptyList())
        }
    }

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
        val inflater = LayoutInflater.from(context)
        val binding: ListGroupBinding =
            if (convertView == null) {
                ListGroupBinding.inflate(inflater, parent, false)
            } else {
                ListGroupBinding.bind(convertView)
            }
        val iconResId = if (isExpanded) R.drawable.ic_close else R.drawable.ic_settings
        binding.listGroup.setImageResource(iconResId)

        return binding.root
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
        bindingListItem = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            ListItemBinding.inflate(inflater, parent, false)
        } else {
            ListItemBinding.bind(convertView)
        }
        setChildrenData(childPosition, bindingListItem)
        return bindingListItem.root
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return groupData.size
    }

    private fun setChildrenData(
        childPosition: Int,
        binding: ListItemBinding
    ) {
        val iconResId = when (childPosition) {
            ICON_KEY_CHILD_MAP_LAYERS -> R.drawable.ic_map_layers
            ICON_KEY_CHILD_TRACK_INFO -> R.drawable.ic_track_info
            ICON_KEY_CHILD_TRACK_INFO_SPEED -> R.drawable.ic_track_info_speed
            ICON_KEY_CHILD_MAP_RELOAD -> R.drawable.ic_map_reload
            ICON_KEY_CHILD_MAP_CENTER -> R.drawable.ic_map_center
            ICON_KEY_CHILD_TRACK_ROUTE -> R.drawable.ic_track_route
            else -> 0
        }
        binding.listItem.setImageResource(iconResId)

        val tintRed = ContextCompat.getColorStateList(context, R.color.colorAccent)
        val tintBlack = ContextCompat.getColorStateList(context, R.color.colorText)
        val iconTint = when (childPosition) {
            ICON_KEY_CHILD_MAP_LAYERS -> if (isExpandedChild[childPosition] == true) tintRed else tintBlack
            ICON_KEY_CHILD_TRACK_INFO -> if (isExpandedChild[childPosition] == true) tintRed else tintBlack
            ICON_KEY_CHILD_TRACK_INFO_SPEED -> if (isExpandedChild[childPosition] == true) tintRed else tintBlack
            ICON_KEY_CHILD_MAP_RELOAD -> tintBlack
            ICON_KEY_CHILD_MAP_CENTER -> tintBlack
            ICON_KEY_CHILD_TRACK_ROUTE -> if (isExpandedChild[childPosition] == true) tintRed else tintBlack
            else -> tintBlack
        }
        binding.listItem.imageTintList = iconTint
    }

    fun setGroupExpanded(isExpandedGroup: Boolean) {
        this.isExpandedGroup = isExpandedGroup
        notifyDataSetChanged()
    }

    fun setChildExpanded(isExpandedChild: Boolean, childPosition: Int) {
        // Clearing the list to see next button selected
        this.isExpandedChild.forEach {
            Log.i("MADS", "setChildExpanded: $it")
            this.isExpandedChild[it.key] = false
        }
        this.isExpandedChild[childPosition] = isExpandedChild
        notifyDataSetChanged()
    }

}
