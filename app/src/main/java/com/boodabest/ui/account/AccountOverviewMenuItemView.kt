package com.boodabest.ui.account

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.boodabest.R
import kotlinx.android.synthetic.main.fragment_account_overview_menu_item.view.*

class AccountOverviewMenuItemView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.fragment_account_overview_menu_item, this)
    }

    fun setTitle(text: String) {
        txtTitle?.text = text
    }

}