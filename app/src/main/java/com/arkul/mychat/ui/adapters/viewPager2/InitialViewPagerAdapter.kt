package com.arkul.mychat.ui.adapters.viewPager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arkul.mychat.ui.fragments.login.LoginFragment
import com.arkul.mychat.ui.fragments.register.RegisterFragment



class InitialViewPagerAdapter(
    private val listOfFragments: ArrayList<Fragment>,
    fragment: Fragment
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return listOfFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return listOfFragments[position]
    }
}