package com.github.matheusadsantos.expandablelistview

// ExpandableListAdapter.kt
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.core.content.ContextCompat
import com.github.matheusadsantos.expandablelistview.ChildButtonInfo.ChildButtonInfo
import com.github.matheusadsantos.expandablelistview.databinding.ListGroupBinding
import com.github.matheusadsantos.expandablelistview.databinding.ListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpandableListAdapter(private val context: Context, private val groupIconKey: Int) :
    BaseExpandableListAdapter() {

    companion object {
        const val ICON_KEY_PARENT_SETTING = 0 // settings (parent)
        const val ICON_KEY_PARENT_CLOSE = 1 // close (parent)

        const val ICON_KEY_CHILD_MAP_LAYERS = 0
        const val ICON_KEY_CHILD_TRACK_INFO = 1
        const val ICON_KEY_CHILD_TRACK_INFO_SPEED = 2
        const val ICON_KEY_CHILD_MAP_RELOAD = 3
        const val ICON_KEY_CHILD_MAP_CENTER = 4
        const val ICON_KEY_CHILD_TRACK_ROUTE = 5
    }

    private var isExpanded = false
    private val groupData = listOf("Parent")
    val childData = listOf(
        "mapLayears",
        "trackInfo",
        "trackInfoSpeedIg",
        "mapReload",
        "mapCenter",
        "trackRoute"
    )

    // Create a map to name/image buttons
    private val childButtonsMap = mutableMapOf<Int, ChildButtonInfo>()

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
                    com.google.android.material.R.drawable.test_level_drawable
                )
            )

            ICON_KEY_CHILD_TRACK_INFO_SPEED -> ChildButtonInfo(
                listOf("Velocidade", "Ignição", "test..."),
                listOf(
                    R.drawable.ic_track_info_speed,
                    R.drawable.ic_track_info_ignition,
                    com.google.android.material.R.drawable.test_level_drawable
                )
            )

            else -> ChildButtonInfo(emptyList(), emptyList())
        }
    }

    fun setUpInfoChildrenButtons(
        groupPosition: Int,
        childPosition: Int,
        buttonsName: List<String>,
        buttonsImage: List<Int>
    ) {
        childButtonsMap[childPosition] = ChildButtonInfo(buttonsName, buttonsImage)
        notifyDataSetChanged()
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
        val binding: ListGroupBinding = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
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
        val binding: ListItemBinding = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            ListItemBinding.inflate(inflater, parent, false)
        } else {
            ListItemBinding.bind(convertView)
        }

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

        CoroutineScope(Dispatchers.Main).launch {
            val childButtonsInfo = childButtonsMap[childPosition]
            childButtonsInfo?.let { setUpInfoButtons(binding, it, childPosition) }
        }

        return binding.root
    }

    private fun setUpInfoButtons(
        binding: ListItemBinding,
        childButtonInfo: ChildButtonInfo,
        position: Int
    ) {
        Log.i("MADS", "setUpInfoButtons: $childButtonInfo")
        binding.button0.visibility =
            if (childButtonInfo.names.isNotEmpty()) View.VISIBLE else View.GONE
        binding.button1.visibility =
            if (childButtonInfo.names.size >= 2) View.VISIBLE else View.GONE
        binding.button2.visibility =
            if (childButtonInfo.names.size >= 3 && position == 0) View.VISIBLE else View.GONE

        binding.button0.text =
            if (childButtonInfo.names.isNotEmpty()) childButtonInfo.names[0] else return
        binding.button1.text =
            if (childButtonInfo.names.size >= 2) childButtonInfo.names[1] else return
        binding.button2.text =
            if (childButtonInfo.names.size >= 3) childButtonInfo.names[2] else return

        val drawable0 = ContextCompat.getDrawable(context, childButtonInfo.images[0])
        binding.button0.setCompoundDrawablesWithIntrinsicBounds(drawable0, null, null, null)
        val drawable1 = ContextCompat.getDrawable(context, childButtonInfo.images[1])
        binding.button1.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null)
        val drawable2 = ContextCompat.getDrawable(context, childButtonInfo.images[2])
        binding.button2.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null)
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
