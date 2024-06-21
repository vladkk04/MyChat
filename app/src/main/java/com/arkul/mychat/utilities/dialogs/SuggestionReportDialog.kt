package com.arkul.mychat.utilities.dialogs

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.arkul.mychat.databinding.SuggestionReportAlertDialogBinding
import com.arkul.mychat.utilities.extensions.getStateListDrawable
import com.arkul.mychat.utilities.extensions.showToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder


private var isShowingDialog = false

enum class ReportCategory(val drawableFill: Int, val drawableOutline: Int) {
    SPAM(R.drawable.ic_delete_fill, R.drawable.ic_delete_outline),
    PERSONAL_INFO(R.drawable.ic_privacy_tip_fill, R.drawable.ic_privacy_tip_outline),
    PORNOGRAPHY(R.drawable.ic_no_adult_content, R.drawable.ic_no_adult_content),
    OTHER(R.drawable.ic_pending_fill, R.drawable.ic_pending_outline),
    NIGGA(R.drawable.ic_pending_fill, R.drawable.ic_pending_outline),
    YA(R.drawable.ic_pending_fill, R.drawable.ic_pending_outline),
    KOXAUY(R.drawable.ic_pending_fill, R.drawable.ic_pending_outline),
    YULIIA(R.drawable.ic_pending_fill, R.drawable.ic_pending_outline),
}

private fun createRadioButton(reportCategory: ReportCategory, context: Context): RadioButton {
    val getFillDrawable = ContextCompat.getDrawable(context, reportCategory.drawableFill)
    val getOutlineDrawable = ContextCompat.getDrawable(context, reportCategory.drawableOutline)

    return RadioButton(context).apply {
        text = reportCategory.name
        buttonDrawable = getStateListDrawable(getFillDrawable, getOutlineDrawable)
        layoutParams = MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 50, 0, 40)
            setPadding(50, 0, 0, 0)
        }
        setOnCheckedChangeListener { _, isChecked ->
            setTypeface(null, if (isChecked) Typeface.BOLD_ITALIC else Typeface.NORMAL)
        }
    }
}

fun Fragment.createSuggestionReportDialog(
    onSendReportListener: (() -> Unit)? = null
): AlertDialog? {
    if (isShowingDialog) {
        return null
    }

    val binding = SuggestionReportAlertDialogBinding.inflate(layoutInflater)
    val viewFlipper = binding.viewFlipper
    val reportCategoryRadioGroup = binding.reportCategoryRadioGroup

    val getNavigateNext = ContextCompat.getDrawable(requireContext(), R.drawable.ic_navigate_next)
    val getNavigateBack = ContextCompat.getDrawable(requireContext(), R.drawable.ic_navigate_back)
    val getNavigateClose = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
    val getDone = ContextCompat.getDrawable(requireContext(), R.drawable.ic_done)
        ?.apply { setBounds(0, 0, 84, 84) }

    isShowingDialog = true

    ReportCategory.entries.forEach { reportCategory ->
        reportCategoryRadioGroup.addView(
            createRadioButton(
                reportCategory, requireContext()
            )
        )
    }

    val dialog = MaterialAlertDialogBuilder(requireContext())
        .setPositiveButtonIcon(getNavigateNext)
        .setNegativeButtonIcon(getNavigateBack)
        .setNeutralButtonIcon(getNavigateClose)
        .setCancelable(false)
        .setView(binding.root)
        .setTitle("Suggestion Report")
        .create()

    return dialog.apply {
        this.setOnShowListener {
            val positiveButton = getButton(AlertDialog.BUTTON_POSITIVE)

            positiveButton.apply {
                setOnClickListener {
                    if (reportCategoryRadioGroup.checkedRadioButtonId == -1) {
                        showToast("Please select a category")
                        return@setOnClickListener
                    } else if (viewFlipper.displayedChild < viewFlipper.size - 1) {
                        viewFlipper.showNext()
                        setCompoundDrawables(getDone, null, null, null)
                    } else {
                        onSendReportListener?.invoke()
                        isShowingDialog = false
                        dismiss()
                    }
                }
            }
            getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                setOnClickListener {
                    getNavigateNext?.apply { setBounds(0, 0, 96, 96) }

                    positiveButton.setCompoundDrawables(
                        getNavigateNext,
                        null,
                        null,
                        null
                    )

                    if (viewFlipper.displayedChild != 0) {
                        viewFlipper.showPrevious()
                    }
                }
            }
            getButton(AlertDialog.BUTTON_NEUTRAL).apply {
                setOnClickListener { _ ->
                    isShowingDialog = false
                    dismiss()
                }
            }
        }
    }
}
