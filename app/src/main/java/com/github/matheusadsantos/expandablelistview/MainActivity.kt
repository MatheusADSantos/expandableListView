package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
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

    private fun setUpChildListener() {
        expandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Log.e("MADS", "setUpChildListener: \nparent: $parent \nv: $v \ngroupPosition: $groupPosition \nchildPosition: $childPosition \nid: $id")
            true
        }
    }

    private fun setUpGroupListener() {
        expandableListView.setOnGroupExpandListener { _ ->
            adapter.setGroupExpanded(true)
        }
        expandableListView.setOnGroupCollapseListener { _ ->
            adapter.setGroupExpanded(false)
        }
    }

    private fun setUpAdapter() {
        expandableListView = binding.expandableListViewButtons
        val groupIconKey = ExpandableListAdapter.ICON_KEY_PARENT_SETTING
        adapter = ExpandableListAdapter(this, groupIconKey)
        expandableListView.setAdapter(adapter)
    }

}
