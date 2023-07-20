package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.matheusadsantos.expandablelistview.databinding.MainActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var clicked: Boolean = false
    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var expandableListViewButtons: ExpandableListView
    private lateinit var buttonClusterButtons: Button
    private val imageClusterButtons = listOf(
        R.drawable.ic_launcher_background,
        com.google.android.material.R.drawable.ic_m3_chip_check,
        com.google.android.material.R.drawable.ic_clock_black_24dp,
        com.google.android.material.R.drawable.ic_search_black_24,
        androidx.constraintlayout.widget.R.drawable.abc_ic_go_search_api_material
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        clicked = false
        setupExpandableListButtons()
        setupButtonCluster()
    }

    private fun setupButtonCluster() {
        buttonClusterButtons = binding.buttonClusterButtons
        buttonClusterButtons.setOnClickListener {
            clicked = !clicked
            Toast.makeText(this, "Clicked: $clicked(${expandableListViewButtons.visibility})", Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.Main).launch {
                if (clicked) {
                    expandableListViewButtons.visibility = View.VISIBLE
                } else {
                    expandableListViewButtons.visibility = View.INVISIBLE
                }
            }
//            expandableListViewButtons.expandGroup(1, true)
        }
    }

    private fun setupExpandableListButtons() {
        expandableListViewButtons = binding.expandableListViewButtons
        val expandableListAdapter = ExpandableListAdapter()
        expandableListViewButtons.setAdapter(expandableListAdapter)
    }

    inner class ExpandableListAdapter : BaseExpandableListAdapter() {

        override fun getGroupCount(): Int {
            return imageClusterButtons.size
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return 1
        }

        override fun getGroup(groupPosition: Int): Any {
            return imageClusterButtons[groupPosition]
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            return childPosition
        }

        override fun getGroupId(groupPosition: Int): Long {
            return groupPosition.toLong()
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            return childPosition.toLong()
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
            val imageView = ImageView(this@MainActivity)
            imageView.setImageResource(imageClusterButtons[groupPosition])
            imageView.layoutParams = LinearLayout.LayoutParams(200, 200)
            return imageView
        }

        override fun getChildView(
            groupPosition: Int,
            childPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            val buttonLayout = LinearLayout(this@MainActivity)
            buttonLayout.orientation = LinearLayout.HORIZONTAL

            for (i in 1..5) {
                val button = Button(this@MainActivity)
                button.text = "Button $i"
                buttonLayout.addView(button)
            }

            return buttonLayout
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return true
        }

    }

}