package com.example.expandview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tcqq.expandview.ExpandView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ExpandView.OnExpandChangedListener {

    companion object {
        private const val INTRODUCTION_MAX_LINE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expand(false)
        introduction.post {
            val lineCount = introduction.lineCount
            if (lineCount > INTRODUCTION_MAX_LINE) {
                introduction_expand.isVisible = true
            }
        }
        introduction_expand.setOnExpandChangedListener(this)
    }

    override fun onExpandChanged(view: ExpandView, expanded: Boolean) {
        expand(expanded)
    }

    private fun expand(expanded: Boolean) {
        introduction.maxLines = if (expanded) Integer.MAX_VALUE else INTRODUCTION_MAX_LINE
    }
}
