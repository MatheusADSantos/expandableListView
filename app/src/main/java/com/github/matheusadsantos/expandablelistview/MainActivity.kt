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

        // Defina o ícone do pai aqui (1, 2 ou 3)
        val groupIconKey = ExpandableListAdapter.ICON_KEY_PARENT_1

        expandableListView = findViewById(R.id.expandableListViewButtons)
        adapter = ExpandableListAdapter(this, groupIconKey)
        expandableListView.setAdapter(adapter)

        expandableListView.setOnGroupExpandListener { groupPosition ->
            // Aqui você pode mudar a imagem do ícone do pai expandindo com base no groupPosition
        }
    }
}
