package com.arkul.mychat.utilities.date

import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

val Fragment.getLocalDatePattern: String
    get() = (DateFormat.getDateFormat(requireContext()) as SimpleDateFormat).toLocalizedPattern()