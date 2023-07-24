package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: ExpandableListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val groupIconKey = ExpandableListAdapter.ICON_KEY_PARENT_SETTING

        expandableListView = findViewById(R.id.expandableListViewButtons)
        adapter = ExpandableListAdapter(this, groupIconKey)
        expandableListView.setAdapter(adapter)

        expandableListView.setOnGroupExpandListener { groupPosition ->
            adapter.setGroupExpanded(true) // Altera o ícone do grupo para expandido
        }

        expandableListView.setOnGroupCollapseListener { groupPosition ->
            adapter.setGroupExpanded(false) // Altera o ícone do grupo para colapsado
        }
    }
}
