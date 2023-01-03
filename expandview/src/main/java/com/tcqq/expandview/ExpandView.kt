package com.tcqq.expandview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.tcqq.expandview.databinding.ViewExpandBinding

/**
 * @author Perry Lance
 * @since 2019-02-28 Created
 */
class ExpandView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs),
    View.OnClickListener {

    private var binding: ViewExpandBinding

    private var moreText: String

    @DrawableRes
    private var moreIcon: Int
    private var lessText: String

    @DrawableRes
    private var lessIcon: Int
    private var expanded: Boolean = false

    @StyleRes
    var expandTextAppearance: Int = DEFAULT_EXPAND_TEXT_APPEARANCE
        set(value) {
            TextViewCompat.setTextAppearance(binding.expandText, expandTextAppearance)
            field = value
        }

    @ColorInt
    var expandTextColor: Int = primaryColor(context)
        set(value) {
            binding.expandText.setTextColor(value)
            field = value
        }

    @ColorInt
    var expandIconColor: Int = primaryColor(context)
        set(value) {
            binding.expandIcon.setColorFilter(value)
            field = value
        }

    fun setExpanded(expanded: Boolean, withoutListener: Boolean = false) {
        this.expanded = expanded
        binding.expandText.text = if (expanded) moreText else lessText
        binding.expandIcon.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                if (expanded) moreIcon else lessIcon
            )
        )
        if (withoutListener.not()) onExpandChangedListener?.onExpandChanged(this, expanded)
    }

    fun getExpanded(): Boolean {
        return expanded
    }

    private var onExpandChangedListener: OnExpandChangedListener? = null

    fun setOnExpandChangedListener(listener: OnExpandChangedListener) {
        onExpandChangedListener = listener
    }

    interface OnExpandChangedListener {
        fun onExpandChanged(view: ExpandView, expanded: Boolean)
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ExpandView, 0, 0
        )

        val moreText = a.getString(R.styleable.ExpandView_expand_more_text) ?: DEFAULT_MORE_TEXT
        val moreIcon = a.getResourceId(R.styleable.ExpandView_expand_more_icon, DEFAULT_MORE_ICON)
        val lessText = a.getString(R.styleable.ExpandView_expand_less_text) ?: DEFAULT_LESS_TEXT
        val lessIcon = a.getResourceId(R.styleable.ExpandView_expand_less_icon, DEFAULT_LESS_ICON)
        val expanded = a.getBoolean(R.styleable.ExpandView_expand_expanded, DEFAULT_EXPANDED)
        val expandTextAppearance =
            a.getResourceId(
                R.styleable.ExpandView_expand_text_appearance,
                DEFAULT_EXPAND_TEXT_APPEARANCE
            )
        val expandTextColor =
            a.getColor(R.styleable.ExpandView_expand_text_color, primaryColor(context))
        val expandIconColor =
            a.getColor(R.styleable.ExpandView_expand_icon_color, primaryColor(context))
        a.recycle()

        binding = ViewExpandBinding.inflate(LayoutInflater.from(context), this, true)

        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        setOnClickListener(this)

        this.moreText = moreText
        this.moreIcon = moreIcon
        this.lessText = lessText
        this.lessIcon = lessIcon
        this.expandTextAppearance = expandTextAppearance
        this.expandTextColor = expandTextColor
        this.expandIconColor = expandIconColor
        setExpanded(expanded)
    }

    @ColorInt
    private fun primaryColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
        return value.data
    }

    override fun onClick(view: View?) {
        setExpanded(expanded.not())
    }

    companion object {
        private const val DEFAULT_EXPANDED = false
        private val DEFAULT_EXPAND_TEXT_APPEARANCE =
            R.style.TextAppearance_MaterialComponents_Button
        private const val DEFAULT_MORE_TEXT = "See less"
        private val DEFAULT_MORE_ICON = R.drawable.ic_expand_less_black_24dp
        private const val DEFAULT_LESS_TEXT = "See more"
        private val DEFAULT_LESS_ICON = R.drawable.ic_expand_more_black_24dp
    }
}
