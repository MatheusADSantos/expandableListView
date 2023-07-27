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
            Log.e("MADS", "setUpChildListener: $groupPosition \nchildPosition: $childPosition")
            var buttonsName = listOf<String>()
            var buttonsImage = listOf<Int>()
            when (childPosition) {
                0 -> {
                    buttonsName = listOf("Padrão", "Satélite", "Trânsito")
                    buttonsImage = listOf(
                        R.drawable.ic_map_layer_default,
                        R.drawable.ic_map_layer_satellite,
                        R.drawable.ic_map_layer_traffic
                    )
                }

                1 -> {
                    buttonsName = listOf("Placa", "Descrição")
                    buttonsImage = listOf(
                        R.drawable.ic_track_info_plate,
                        R.drawable.ic_track_info_description
                    )
                }

                2 -> {
                    buttonsName = listOf("Velocidade", "Ignição")
                    buttonsImage = listOf(
                        R.drawable.ic_track_info_speed,
                        R.drawable.ic_track_info_ignition
                    )
                }
            }
            setUpButtonsFromChild(childPosition, groupPosition, buttonsName, buttonsImage)
            true
        }
    }

    private fun setUpButtonsFromChild(
        childPosition: Int,
        groupPosition: Int,
        buttonsName: List<String>,
        buttonsImage: List<Int>
    ) {
        adapter.childData.forEachIndexed { index, _ ->
            if (index != childPosition) {
                adapter.setChildButtons(groupPosition, index, emptyList(), emptyList())
            }
        }
        adapter.setChildButtons(groupPosition, childPosition, buttonsName, buttonsImage)

        for (i in 0 until adapter.groupCount) {
            if (i != groupPosition) {
                adapter.setChildButtons(i, 0, emptyList(), emptyList())
            }
        }
    }

    private fun setUpGroupListener() {
        expandableListView.setOnGroupExpandListener { groupPosition ->
            adapter.setGroupExpanded(true)
            adapter.childData.forEachIndexed { index, _ ->
                adapter.setChildButtons(groupPosition, index, emptyList(), emptyList())
            }
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
