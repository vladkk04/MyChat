package com.arkul.mychat.data.di.modules

import com.arkul.mychat.data.network.firebase.impl.CredentialRepository
import com.arkul.mychat.data.network.firebase.impl.EmailRepository
import com.arkul.mychat.data.network.firebase.services.AccountService
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
        credentialRepository: EmailRepository
    ): EmailService
}