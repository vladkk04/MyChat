package com.arkul.mychat.utilities.date

import android.content.DialogInterface
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.InputMode
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.text.SimpleDateFormat
import java.util.Locale

fun Fragment.createDatePicker(
    titleText: String = "Select dates",
    @InputMode inputMode: Int = MaterialDatePicker.INPUT_MODE_TEXT,
    validator: DateValidator = DateValidatorPointBackward.now(),
    constraintBuilder: CalendarConstraints.Builder = CalendarConstraints.Builder()
        .setValidator(validator),
    onPositiveButtonCallBack: MaterialPickerOnPositiveButtonClickListener<Long>? = null,
    onNegativeButtonCallBack: View.OnClickListener? = null,
    onCancelButtonCallBack: DialogInterface.OnCancelListener? = null,
    onDismissButtonCallBack: DialogInterface.OnDismissListener? = null,
) = MaterialDatePicker.Builder.datePicker()
    .setTitleText(titleText)
    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
    .setCalendarConstraints(constraintBuilder.build())
    .setInputMode(inputMode)
    .setTextInputFormat(SimpleDateFormat(getLocalDatePattern, Locale.getDefault()))
    .build().apply {
        onPositiveButtonCallBack?.let { this.addOnPositiveButtonClickListener(onPositiveButtonCallBack) }
        onNegativeButtonCallBack?.let { this.addOnNegativeButtonClickListener(onNegativeButtonCallBack) }
        onCancelButtonCallBack?.let { this.addOnCancelListener(onCancelButtonCallBack) }
        onDismissButtonCallBack?.let { this.addOnDismissListener(onDismissButtonCallBack) }
    }