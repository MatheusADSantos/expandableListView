package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.github.matheusadsantos.expandablelistview.ChildButtonInfo.ChildButtonInfo
import com.github.matheusadsantos.expandablelistview.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

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
        val groupIconKey = ExpandableListAdapter.ICON_KEY_PARENT_SETTING
        adapter = ExpandableListAdapter(this, groupIconKey)
        expandableListView.setAdapter(adapter)
    }


    private fun setUpGroupListener() {
        expandableListView.setOnGroupExpandListener { groupPosition ->
            adapter.setGroupExpanded(true)
            adapter.childData.forEachIndexed { index, _ ->
                setUpInfoChildrenButtons(
                    index, groupPosition, childButtonInfo = ChildButtonInfo(
                        listOf(), listOf()
                    )
                )
            }
        }
        expandableListView.setOnGroupCollapseListener { _ ->
            adapter.setGroupExpanded(false)
        }
    }

    private fun setUpChildListener() {
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Log.e("MADS", "setUpChildListener: $groupPosition \nchildPosition: $childPosition")
            var childButtonInfo = adapter.getChildButtonInfo(childPosition)
            setUpInfoChildrenButtons(groupPosition, childPosition, childButtonInfo)
            true
        }
    }

    private fun setUpInfoChildrenButtons(
        groupPosition: Int,
        childPosition: Int,
        childButtonInfo: ChildButtonInfo
    ) {
        adapter.childData.forEachIndexed { index, _ ->
            if (index != childPosition) {
                adapter.setUpInfoChildrenButtons(groupPosition, index, emptyList(), emptyList())
            } else {
                adapter.setUpInfoChildrenButtons(
                    groupPosition,
                    childPosition,
                    childButtonInfo.names,
                    childButtonInfo.images
                )
            }
        }
    }

}
