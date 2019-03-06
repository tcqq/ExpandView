package com.example.expandview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tcqq.expandview.ExpandView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val INTRODUCTION_MAX_LINE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        introduction.post {
            val lineCount = introduction.lineCount
            if (lineCount > INTRODUCTION_MAX_LINE) {
                introduction_expand.isVisible = true
                introductionExpand(false)
                introduction_expand.setOnClickListener {
                    introductionExpand(true)
                }
            }
        }
        introduction_expand.addOnExpandChangedListener(object: ExpandView.OnExpandChangedListener {
            override fun onExpandChanged(view: ExpandView, expanded: Boolean) {
                Log.d("MainActivity", "expanded: $expanded")
            }
        })
    }

    private fun introductionExpand(switched: Boolean) {
        if (if (switched) introduction_expand.expanded else !introduction_expand.expanded) {
            introduction.maxLines = INTRODUCTION_MAX_LINE
            introduction_expand.expanded = false
        } else {
            introduction.maxLines = Integer.MAX_VALUE
            introduction_expand.expanded = true
        }
    }
}
