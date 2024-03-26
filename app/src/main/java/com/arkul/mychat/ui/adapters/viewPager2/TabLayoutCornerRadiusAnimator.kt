package com.arkul.mychat.ui.adapters.viewPager2

import android.graphics.drawable.GradientDrawable
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlin.math.abs


enum class Side {
    LEFT,
    RIGHT
}

class TabLayoutCornerRadiusAnimator(
    tabLayout: TabLayout,
    private val cornerRadius: Int = 20,
    private val cornerStartedSide: Side = Side.LEFT
) : ViewPager2.OnPageChangeCallback() {
    private val shapeDrawable = tabLayout.tabSelectedIndicator as GradientDrawable
    private var isInitialize = false

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)

        val cornerRadius = abs(((positionOffset * cornerRadius) - (cornerRadius / 2)) * 2) * 3

        if (positionOffset != 0.00f) {
            val leftNumber = if (positionOffset < 0.5) cornerRadius else 0f
            val rightNumber = if (positionOffset >= 0.5) cornerRadius else 0f

            shapeDrawable.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, leftNumber, leftNumber, rightNumber, rightNumber)
        } else if(!isInitialize) {
            when (cornerStartedSide) {
                Side.LEFT -> shapeDrawable.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, cornerRadius, cornerRadius, 0f, 0f)
                Side.RIGHT -> shapeDrawable.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, cornerRadius, cornerRadius)
            }
            isInitialize = true
        }
        shapeDrawable.invalidateSelf()
    }
}