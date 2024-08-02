package com.arkul.mychat.ui.adapters.recyclerViews

import com.arkul.mychat.data.models.Suggestion

fun interface OnItemClickListener <T> {
    fun onClick(item: T, position: Int)
}