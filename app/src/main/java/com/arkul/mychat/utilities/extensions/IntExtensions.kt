package com.arkul.mychat.utilities.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Int.toDp(context: Context): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
fun Float.toDp(context: Context): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)