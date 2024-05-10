package com.arkul.mychat.data.di.modules

import android.content.Context
import com.arkul.mychat.ui.screens.createProfile.SharedProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ShareProfileModule {

    /*@Provides
    fun provideShareViewModel(@ApplicationContext applicationContext: Context): SharedProfileViewModel {
        return SharedProfileViewModel(applicationContext)
    }*/
}