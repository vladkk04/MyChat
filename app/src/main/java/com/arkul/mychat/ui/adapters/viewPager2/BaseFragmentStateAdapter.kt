package com.arkul.mychat.ui.adapters.viewPager2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BaseFragmentStateAdapter(
    private val listOfFragments: ArrayList<Fragment>,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = listOfFragments.size
    override fun createFragment(position: Int): Fragment = listOfFragments[position]

}