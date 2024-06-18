package com.arkul.mychat.utilities.extensions

import android.text.InputFilter

enum class InputFilterType {
    IS_WHITE_SPACE
}

fun getFilter(vararg inputFilterType: InputFilterType): Array<InputFilter> {
    return inputFilterType.map { type ->
        when (type) {
            InputFilterType.IS_WHITE_SPACE -> InputFilter { source, start, end, _, _, _ ->
                for (i in start until end) {
                    if (Character.isWhitespace(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        }
    }.toTypedArray()
}

fun getFilter(vararg filter: InputFilter) =
    arrayOf(filter, InputFilter { source, start, end, _, _, _ ->
        for (i in start until end) {
            if (Character.isWhitespace(source[i])) {
                return@InputFilter ""
            }
        }
        null
    })