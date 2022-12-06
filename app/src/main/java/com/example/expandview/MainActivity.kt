package com.example.expandview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.expandview.databinding.ActivityMainBinding
import com.tcqq.expandview.ExpandView

class MainActivity : AppCompatActivity(),
    ExpandView.OnExpandChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        expand(false)
        binding.introduction.post {
            val lineCount = binding.introduction.lineCount
            if (lineCount > INTRODUCTION_MAX_LINE) {
                binding.introductionExpand.isVisible = true
            }
        }
        binding.introductionExpand.setOnExpandChangedListener(this)
    }

    override fun onExpandChanged(view: ExpandView, expanded: Boolean) {
        expand(expanded)
    }

    private fun expand(expanded: Boolean) {
        binding.introduction.maxLines = if (expanded) Integer.MAX_VALUE else INTRODUCTION_MAX_LINE
    }

    companion object {
        private const val INTRODUCTION_MAX_LINE = 5
    }
}
