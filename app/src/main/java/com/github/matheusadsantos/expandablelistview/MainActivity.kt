package com.github.matheusadsantos.expandablelistview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.matheusadsantos.expandablelistview.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private var clicked: Boolean = false
    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private lateinit var expandableListViewButtons: ExpandableListView
    private lateinit var buttonClusterButtons: ImageButton
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
        setupButtonCluster()
        setupExpandableListButtons()
        setupInicial()
    }

    private fun setupInicial() {
        clicked = false
        expandableListViewButtons.visibility = View.GONE
    }

    private fun setupButtonCluster() {
        buttonClusterButtons = binding.buttonClusterButtons
        buttonClusterButtons.setOnClickListener {
            clicked = !clicked
            runOnUiThread {
                if (clicked) {
                    Toast.makeText(this@MainActivity, "Clicked: ${this.clicked}", Toast.LENGTH_SHORT).show()
                    expandableListViewButtons.visibility = View.VISIBLE
//                    expandableListViewButtons.transcriptMode = ExpandableListView.CHOICE_MODE_MULTIPLE_MODAL
                    buttonClusterButtons.setBackgroundResource(R.drawable.background_button_cluster_close)
                } else {
                    expandableListViewButtons.visibility = View.GONE
                    buttonClusterButtons.setBackgroundResource(R.drawable.background_button_cluster_open)
                }
            }
//            expandableListViewButtons.expandGroup(1, true) // already make the button expandable, in position 1
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
//            imageView.layoutParams = LinearLayout.LayoutParams(60, 60)
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
            buttonLayout.orientation = LinearLayout.VERTICAL

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