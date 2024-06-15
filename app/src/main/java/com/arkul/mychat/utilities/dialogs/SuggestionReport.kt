package com.arkul.mychat.utilities.dialogs

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.arkul.mychat.R
import com.arkul.mychat.databinding.SuggestionReportAlertDialogBinding
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

fun getStateListDrawable(isSelectable: Drawable?, isNotSelectable: Drawable?): StateListDrawable {
    val stateListDrawable = StateListDrawable()

    val stateChecked = intArrayOf(android.R.attr.state_checked)
    val stateUnchecked = intArrayOf(-android.R.attr.state_checked)

    stateListDrawable.addState(stateChecked, isSelectable)
    stateListDrawable.addState(stateUnchecked, isNotSelectable)

    stateListDrawable.addState(intArrayOf(), isSelectable)

    return stateListDrawable
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
            setMargins(0, 90, 0, 0)
            setPadding(50, 0, 0, 0)
        }
        setOnCheckedChangeListener { _, isChecked ->
            setTypeface(null, if (isChecked) Typeface.BOLD_ITALIC else Typeface.NORMAL)
        }
    }
}

fun Fragment.createSuggestionReportDialog(): AlertDialog? {
    if (isShowingDialog) { return null }
    isShowingDialog = true
    val binding = SuggestionReportAlertDialogBinding.inflate(layoutInflater)
    val viewFlipper = binding.viewFlipper
    val reportCategoryRadioGroup = binding.reportCategoryRadioGroup

    ReportCategory.entries.forEach { reportCategory ->
        reportCategoryRadioGroup.addView(
            createRadioButton(
                reportCategory, requireContext()
            )
        )
    }

    return MaterialAlertDialogBuilder(requButtonBarLayout
            ireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog)
        .setPositiveButtonIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_navigate_next
            )
        )
        .setNegativeButtonIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_navigate_back
            )
        )
        .setNeutralButtonIcon(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_close
            )
        )
        .setCancelable(false)
        .setView(binding.root)
        .setTitle("Suggestion Report")
        .create().apply {
            this.setOnShowListener {
                getButton(AlertDialog.BUTTON_POSITIVE).apply {
                    setOnClickListener {
                        if (viewFlipper.displayedChild < viewFlipper.size - 1) {
                            viewFlipper.showNext()
                        }
                    }
                }
                getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                    setOnClickListener {
                        viewFlipper.showPrevious()
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
