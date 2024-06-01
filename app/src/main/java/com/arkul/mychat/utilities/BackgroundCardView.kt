package com.arkul.mychat.utilities

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.arkul.mychat.utilities.extensions.toDp
import com.google.android.material.card.MaterialCardView

class BackgroundCardView(val context: Context) {
    fun createView(
        width: Int = 35,
        height: Int = 35,
        radius: Float = 50f,
        cardElevation: Float = 0f,
        backgroundColor: Int = Color.BLACK,
        marginHorizontalDp: Int = 10, // Horizontal margin in dp
        sizeToDp: Boolean = true,
        setOnClickListener: ((color: Int) -> Unit)? = null
    ): CardView {
        val cardView = MaterialCardView(context)
        val currentWidth = if (sizeToDp) width.toDp(context) else width
        val currentHeight = if (sizeToDp) height.toDp(context) else height

        val currentMarginHorizontal = if (sizeToDp) marginHorizontalDp.toDp(context) else marginHorizontalDp
        val layoutParams = ViewGroup.MarginLayoutParams(currentWidth, currentHeight)

        layoutParams.setMargins(currentMarginHorizontal, 0, currentMarginHorizontal, 0)

        cardView.layoutParams = layoutParams
        cardView.radius = radius

        cardView.cardElevation = cardElevation
        cardView.setCardBackgroundColor(backgroundColor)

        cardView.setOnClickListener {
            setOnClickListener?.invoke(backgroundColor)
        }
        return cardView
    }

}
