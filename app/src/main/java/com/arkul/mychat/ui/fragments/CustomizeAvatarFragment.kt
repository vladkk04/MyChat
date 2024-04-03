package com.arkul.mychat.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.arkul.mychat.R
import com.arkul.mychat.databinding.FragmentCustomizeAvatarBinding
import com.arkul.mychat.ui.adapters.viewPager2.SelectAvatarAdapter
import kotlin.math.abs

class CustomizeAvatarFragment : Fragment() {

    private var _binding: FragmentCustomizeAvatarBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: SelectAvatarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomizeAvatarBinding.inflate(inflater, container, false)

        viewPagerAdapter = SelectAvatarAdapter(
            arrayListOf(
                R.drawable.avatar_default_1,
                R.drawable.avatar_default_2,
                R.drawable.avatar_default_3,
                R.drawable.avatar_default_4,
                R.drawable.avatar_default_5
            )
        )

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

        val pageTransformer = CompositePageTransformer()

        pageTransformer.addTransformer { page, position ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )


        with(binding.viewPager) {
            adapter = viewPagerAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            currentItem = 2
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(pageTransformer)
            addItemDecoration(itemDecoration)
        }

        return binding.root
    }
}