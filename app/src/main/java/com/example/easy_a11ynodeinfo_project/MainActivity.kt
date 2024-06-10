package com.example.easy_a11ynodeinfo_project

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.easy_a11ynodeinfo.AccessibilityImportance
import com.example.easy_a11ynodeinfo.AccessibilityRole
import com.example.easy_a11ynodeinfo.nodeInfo
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getChildViewsOf:(parentView:ViewGroup)->MutableList<View> = {
            val list: MutableList<View> = mutableListOf()
            for (index in 0 until it.childCount) {
                list.add(it.getChildAt(index))
            }
            list
        }

        val hwTablist:LinearLayout = findViewById(R.id.hwlist)

        val hwTexts = getChildViewsOf(hwTablist)

        hwTablist.nodeInfo
            .setRole(AccessibilityRole.LIST)
            .setItemCount(hwTexts.size)

        hwTexts.forEachIndexed { index, view ->
            
            view.nodeInfo.setRole(AccessibilityRole.SWITCH)
                .setItemIndex(index)


//            view.setOnClickListener { clickedTab ->
//                hwTexts.forEach {
//                    it.nodeInfo.setSelected(clickedTab == it)
//                }
//            }
        }
    }
}