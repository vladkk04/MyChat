package com.arkul.mychat.data.di.modules

import android.app.Application
import android.content.Context
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import com.arkul.mychat.MainActivity
import com.arkul.mychat.data.di.MyChatApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    /*@Provides
    @ActivityScoped
    fun provideActivityResultRegistry(@ActivityContext appContext: AppCompatActivity): ActivityResultRegistry {
        return appContext.activityResultRegistry
    }*/
}