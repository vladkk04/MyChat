package com.arkul.mychat.utilities.date

import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val Fragment.getLocalDatePattern: String
    get() = (DateFormat.getDateFormat(requireContext()) as SimpleDateFormat).toLocalizedPattern()

val getCurrentTime_AM_PM: String
    get() = DateFormat.format("hh:mm a", Calendar.getInstance()).toString()