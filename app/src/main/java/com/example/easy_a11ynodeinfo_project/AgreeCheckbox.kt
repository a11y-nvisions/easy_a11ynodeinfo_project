package com.example.easy_a11ynodeinfo_project

import android.annotation.SuppressLint
import android.content.Context
import android.text.util.Linkify
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import java.util.regex.Pattern

@SuppressLint("Recycle")
class AgreementCheckBox(context: Context, attrs:AttributeSet?): LinearLayout(context,attrs) {

    init {
        val body = LayoutInflater.from(context).inflate(R.layout.agreement_checkbox_layout,this,true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it,R.styleable.AgreementCheckBox)
            val linkTextPattern = typedArray.getString(R.styleable.AgreementCheckBox_link_text_pattern)
            val linkUrl = typedArray.getString(R.styleable.AgreementCheckBox_link_url)
            val labelText = typedArray.getString(R.styleable.AgreementCheckBox_label_text)
            typedArray.recycle()

            val labelView: TextView = findViewById(R.id.lb_agreement_checkbox)

            labelView.apply {
                text = labelText
                val transform = Linkify.TransformFilter { match, url -> ""  }
                linkTextPattern?.let { text ->
                    val pattern = Pattern.compile(text)
                    Linkify.addLinks(labelView,pattern,linkUrl,null,transform)
                }

            }
        }
    }
}