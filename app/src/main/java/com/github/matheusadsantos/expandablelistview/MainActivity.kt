package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
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
        expandableListView = binding.expandableListViewButtons

        val groupIconKey = ExpandableListAdapter.ICON_KEY_PARENT_SETTING
        adapter = ExpandableListAdapter(this, groupIconKey)
        expandableListView.setAdapter(adapter)

        expandableListView.setOnGroupExpandListener { groupPosition ->
            adapter.setGroupExpanded(true)
        }

        expandableListView.setOnGroupCollapseListener { groupPosition ->
            adapter.setGroupExpanded(false)
        }
    }
}
