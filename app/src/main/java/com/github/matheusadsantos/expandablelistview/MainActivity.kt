package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.matheusadsantos.expandablelistview.ChildButtonInfo.ChildButtonInfo
import com.github.matheusadsantos.expandablelistview.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private val childButtonsMap = mutableMapOf<Int, ChildButtonInfo>()
    private var isExpandedChild = false
    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: ExpandableListAdapter
    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpAdapter()
        setUpGroupListener()
        setUpChildListener()
    }

    private fun setUpAdapter() {
        expandableListView = binding.expandableListViewButtons
        adapter = ExpandableListAdapter(this)
        expandableListView.setAdapter(adapter)
    }

    private fun setUpGroupListener() {
        expandableListView.setOnGroupExpandListener { _ ->
            adapter.setGroupExpanded(true)
        }
        expandableListView.setOnGroupCollapseListener { _ ->
            adapter.setGroupExpanded(false)
            binding.flexButtons.visibility = View.GONE
        }
    }

    private fun setUpChildListener() {
        expandableListView.setOnChildClickListener { _, _, _, childPosition, _ ->
            val childButtonInfo = adapter.getChildButtonInfo(childPosition)
            setupChildrenData(childPosition, childButtonInfo)
            setupButton(binding, childButtonInfo, childPosition)

            isExpandedChild = !isExpandedChild
            adapter.setChildExpanded(isExpandedChild, childPosition)
            true
        }
    }

    private fun View.dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale).toInt()
    }

    private fun getMarginTop(childPosition: Int): Int {
        val heightButton = 40
        val betweenMargin = 14
        return ((childPosition + 1) * heightButton) + ((childPosition + 1) * betweenMargin)
    }

    private fun setupButton(
        binding: MainActivityBinding,
        childButtonData: ChildButtonInfo,
        childPosition: Int
    ) {
        binding.flexButtons.visibility = View.VISIBLE
        setupMarginTopFlexButtons(childPosition, binding)
        setButtonsData(binding, childButtonData, childPosition)
    }

    private fun setButtonsData(
        binding: MainActivityBinding,
        childButtonData: ChildButtonInfo,
        childPosition: Int
    ) {
        binding.button0.visibility =
            if (childButtonData.names.isNotEmpty()) View.VISIBLE else View.GONE
        binding.button1.visibility =
            if (childButtonData.names.size >= 2) View.VISIBLE else View.GONE
        binding.button2.visibility =
            if (childButtonData.names.size >= 3 && childPosition == 0) View.VISIBLE else View.GONE

        binding.button0.text =
            if (childButtonData.names.isNotEmpty()) childButtonData.names[0] else return
        binding.button1.text =
            if (childButtonData.names.size >= 2) childButtonData.names[1] else return
        binding.button2.text =
            if (childButtonData.names.size >= 3) childButtonData.names[2] else return

        val drawable0 = ContextCompat.getDrawable(this, childButtonData.images[0])
        binding.button0.setCompoundDrawablesWithIntrinsicBounds(drawable0, null, null, null)
        val drawable1 = ContextCompat.getDrawable(this, childButtonData.images[1])
        binding.button1.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null)
        val drawable2 = ContextCompat.getDrawable(this, childButtonData.images[2])
        binding.button2.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null)
    }

    private fun setupMarginTopFlexButtons(
        childPosition: Int,
        binding: MainActivityBinding
    ) {
        val marginTop = when (childPosition) {
            ExpandableListAdapter.ICON_KEY_CHILD_MAP_LAYERS -> {
                binding.flexButtons.dpToPx(getMarginTop(childPosition))
            }

            ExpandableListAdapter.ICON_KEY_CHILD_TRACK_INFO -> {
                binding.flexButtons.dpToPx(getMarginTop(childPosition))
            }

            ExpandableListAdapter.ICON_KEY_CHILD_TRACK_INFO_SPEED -> {
                binding.flexButtons.dpToPx(getMarginTop(childPosition))
            }

            ExpandableListAdapter.ICON_KEY_CHILD_MAP_RELOAD -> {
                binding.flexButtons.dpToPx(getMarginTop(childPosition))
            }

            ExpandableListAdapter.ICON_KEY_CHILD_MAP_CENTER -> {
                binding.flexButtons.dpToPx(getMarginTop(childPosition))
            }

            ExpandableListAdapter.ICON_KEY_CHILD_TRACK_ROUTE -> {
                binding.flexButtons.dpToPx(getMarginTop(childPosition))
            }

            else -> {
                0
            }
        }
        val layoutParams = binding.flexButtons.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = marginTop
        binding.flexButtons.layoutParams = layoutParams
    }

    private fun setupChildrenData(
        childPosition: Int,
        childButtonInfo: ChildButtonInfo
    ) {
        adapter.childData.forEachIndexed { index, _ ->
            if (index != childPosition) {
                childButtonsMap[childPosition] = ChildButtonInfo(emptyList(), emptyList())
            } else {
                childButtonsMap[childPosition] =
                    ChildButtonInfo(childButtonInfo.names, childButtonInfo.images)
            }
        }
    }

}
