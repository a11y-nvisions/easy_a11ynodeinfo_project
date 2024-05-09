package com.example.easy_a11ynodeinfo_project

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easy_a11ynodeinfo.AccessibilityRole
import com.example.easy_a11ynodeinfo.nodeInfo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var checked:Boolean = false
        val hwText:TextView = findViewById(R.id.hw_text)
        hwText.nodeInfo.setRole(AccessibilityRole.SWITCH).setChecked(checked)
        hwText.setOnClickListener {
            checked = !checked
            it.nodeInfo.setChecked(checked)
        }
    }
}