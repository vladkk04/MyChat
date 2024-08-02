package com.arkul.mychat.utilities.extensions

import android.content.Context
import android.graphics.drawable.StateListDrawable
import androidx.core.content.ContextCompat

fun getStateListDrawable(context: Context, isSelectableDrawable: Int, isNotSelectableDrawable: Int): StateListDrawable {
    val getSelectableDrawable = ContextCompat.getDrawable(context, isSelectableDrawable)
    val getNotSelectableDrawable = ContextCompat.getDrawable(context, isNotSelectableDrawable)

    val stateListDrawable = StateListDrawable()

    val stateChecked = intArrayOf(android.R.attr.state_checked)
    val stateUnchecked = intArrayOf(-android.R.attr.state_checked)

    stateListDrawable.addState(stateChecked, getSelectableDrawable)
    stateListDrawable.addState(stateUnchecked, getNotSelectableDrawable)

    stateListDrawable.addState(intArrayOf(), getSelectableDrawable)

    return stateListDrawable
}