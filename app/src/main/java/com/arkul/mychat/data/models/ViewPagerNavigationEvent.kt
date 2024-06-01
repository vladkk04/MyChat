package com.arkul.mychat.data.models

sealed class ViewPagerNavigationEvent {
    data object OnNextPage: ViewPagerNavigationEvent()
    data object OnBackPage: ViewPagerNavigationEvent()
}