package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
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
        adapter = ExpandableListAdapter(this)
        expandableListView.setAdapter(adapter)
    }

    private fun setUpGroupListener() {
        expandableListView.setOnGroupExpandListener { _ ->
            adapter.setGroupExpanded(true)
        }
        expandableListView.setOnGroupCollapseListener { _ ->
            adapter.setGroupExpanded(false)
        }
    }

    private fun setUpChildListener() {
        expandableListView.setOnChildClickListener { _, _, _, childPosition, _ ->
            var childButtonInfo = adapter.getChildButtonInfo(childPosition)
            setUpInfoChildrenButtons(childPosition, childButtonInfo)
            true
        }
    }

    private fun setUpInfoChildrenButtons(
        childPosition: Int,
        childButtonInfo: ChildButtonInfo
    ) {
        adapter.childData.forEachIndexed { index, _ ->
            if (index != childPosition) {
                adapter.setUpInfoChildrenButtons(
                    index,
                    emptyList(),
                    emptyList()
                )
            } else {
                adapter.setUpInfoChildrenButtons(
                    childPosition,
                    childButtonInfo.names,
                    childButtonInfo.images
                )
            }
        }
    }

}
