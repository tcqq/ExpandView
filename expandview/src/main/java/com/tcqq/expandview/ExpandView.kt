package com.tcqq.expandview

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
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
import kotlinx.android.synthetic.main.view_expand.view.*

/**
 * @author Perry Lance
 * @since 2019-02-28 Created
 */
class ExpandView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs),
    View.OnClickListener {

    private var moreText: String
    @DrawableRes
    private var moreIcon: Int
    private var lessText: String
    @DrawableRes
    private var lessIcon: Int
    var expanded: Boolean = false
        set(value) {
            if (value) {
                expand_text.text = moreText
                expand_icon.setImageDrawable(ContextCompat.getDrawable(context, moreIcon))
                onExpandChangedListener?.onExpandChanged(this, true)
            } else {
                expand_text.text = lessText
                expand_icon.setImageDrawable(ContextCompat.getDrawable(context, lessIcon))
                onExpandChangedListener?.onExpandChanged(this, false)
            }
            field = value
        }

    @StyleRes
    var expandTextAppearance: Int = DEFAULT_EXPAND_TEXT_APPEARANCE
        set(value) {
            TextViewCompat.setTextAppearance(expand_text, expandTextAppearance)
            field = value
        }
    @ColorInt
    var expandTextColor: Int = primaryColor(context)
        set(value) {
            expand_text.setTextColor(value)
            field = value
        }
    @ColorInt
    var expandIconColor: Int = primaryColor(context)
        set(value) {
            expand_icon.setColorFilter(value)
            field = value
        }

    private var onExpandChangedListener: OnExpandChangedListener? = null

    fun setOnExpandChangedListener(listener: OnExpandChangedListener) {
        onExpandChangedListener = listener
    }

    interface OnExpandChangedListener {
        fun onExpandChanged(view: ExpandView, expanded: Boolean)
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

        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_expand, this, true)

        setOnClickListener(this)

        this.moreText = moreText
        this.moreIcon = moreIcon
        this.lessText = lessText
        this.lessIcon = lessIcon
        this.expanded = expanded
        this.expandTextAppearance = expandTextAppearance
        this.expandTextColor = expandTextColor
        this.expandIconColor = expandIconColor
    }

    @ColorInt
    private fun primaryColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
        return value.data
    }

    override fun onClick(view: View?) {
        expanded = !expanded
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState()!!)
        savedState.moreText = moreText
        savedState.moreIcon = moreIcon
        savedState.expanded = expanded
        return savedState
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            moreText = state.moreText
            moreIcon = state.moreIcon
            expanded = state.expanded
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    internal class SavedState : BaseSavedState {

        var moreText = DEFAULT_MORE_TEXT
        var moreIcon: Int = R.drawable.ic_expand_less_black_24dp
        var expanded: Boolean = DEFAULT_EXPANDED

        constructor(source: Parcel) : super(source) {
            moreText = source.readString()!!
            moreIcon = source.readInt()
            expanded = source.readByte().toInt() != 0
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(moreText)
            out.writeInt(moreIcon)
            out.writeByte((if (expanded) 1 else 0).toByte())
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
