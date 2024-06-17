package com.arkul.mychat.utilities.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable

fun getStateListDrawable(isSelectable: Drawable?, isNotSelectable: Drawable?): StateListDrawable {
    val stateListDrawable = StateListDrawable()

    val stateChecked = intArrayOf(android.R.attr.state_checked)
    val stateUnchecked = intArrayOf(-android.R.attr.state_checked)

    stateListDrawable.addState(stateChecked, isSelectable)
    stateListDrawable.addState(stateUnchecked, isNotSelectable)

    stateListDrawable.addState(intArrayOf(), isSelectable)

    return stateListDrawable
}