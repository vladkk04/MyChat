package com.arkul.mychat.data.di.modules

import com.arkul.mychat.data.network.firebase.repositories.CredentialRepository
import com.arkul.mychat.data.network.firebase.repositories.EmailRepository
import com.arkul.mychat.data.network.firebase.services.CredentialService
import com.arkul.mychat.data.network.firebase.services.EmailService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindCredentialService(
        credentialRepository: CredentialRepository
    ): CredentialService

    @Binds
    @Singleton
    abstract fun bindEmailService(
        emailRepository: EmailRepository
    ): EmailService
}