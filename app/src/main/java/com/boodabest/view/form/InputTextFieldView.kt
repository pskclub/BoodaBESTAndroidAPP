package com.boodabest.view.form

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.boodabest.R

class InputTextFieldView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.input_text_field, this)
    }

}