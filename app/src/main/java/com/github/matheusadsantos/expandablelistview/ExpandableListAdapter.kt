package com.github.matheusadsantos.expandablelistview

// ExpandableListAdapter.kt
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.github.matheusadsantos.expandablelistview.ChildButtonInfo.ChildButtonsInfo
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
    private val childButtonsMap = mutableMapOf<Int, ChildButtonsInfo>()

    fun setChildButtons(
        groupPosition: Int,
        childPosition: Int,
        buttonsName: List<String>,
        buttonsImage: List<Int>
    ) {
        childButtonsMap[childPosition] = ChildButtonsInfo(buttonsName, buttonsImage)
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
            childButtonsInfo?.let {
                when (it.buttonName.size) {
                    0 -> {
                        setUpInfoButtons(binding, it, listOf(View.GONE, View.GONE, View.GONE))
                    }

                    2 -> {
                        setUpInfoButtons(binding, it, listOf(View.VISIBLE, View.VISIBLE, View.GONE))
                    }

                    3 -> {
                        setUpInfoButtons(
                            binding,
                            it,
                            listOf(View.VISIBLE, View.VISIBLE, View.VISIBLE)
                        )
                    }
                }
            }
        }
        return binding.root
    }

    private fun setUpInfoButtons(
        binding: ListItemBinding,
        it: ChildButtonsInfo,
        visibility: List<Int>
    ) {
        Log.i("MADS", "setUpInfoButtons: $it")
        binding.button1CardView.visibility = visibility[0]
        binding.button2CardView.visibility = visibility[1]
        binding.button3CardView.visibility = visibility[2]

        binding.button1TextView.text =
            if (visibility[0] == View.VISIBLE) it.buttonName[0] else return
        binding.button2TextView.text =
            if (visibility[1] == View.VISIBLE) it.buttonName[1] else return
        binding.button3TextView.text =
            if (visibility[2] == View.VISIBLE) it.buttonName[2] else return


        binding.button1ImageView.setImageResource(it.buttonImage[0])
        binding.button2ImageView.setBackgroundResource(it.buttonImage[1])
        binding.button3ImageView.setBackgroundResource(it.buttonImage[2])
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
