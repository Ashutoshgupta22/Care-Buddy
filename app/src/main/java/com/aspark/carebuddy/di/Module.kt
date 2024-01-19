package com.aspark.carebuddy.di

import com.aspark.carebuddy.api.NurseApi
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitService.retrofit
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideNurseApi(retrofit: Retrofit): NurseApi {
        return retrofit.create(NurseApi::class.java)
    }

    @Provides
    fun provideRepository(userApi: UserApi, nurseApi: NurseApi): Repository {
        return Repository(userApi, nurseApi)
    }

    @Provides
    fun provideXMPPTCPConnectionConfiguration(retrofit: Retrofit): XMPPTCPConnectionConfiguration {

        return XMPPTCPConnectionConfiguration.builder()
            //.setUsernameAndPassword("user1", "user1")
            .setXmppDomain("aspark-care-buddy.ap-south-1.elasticbeanstalk.com")
            .setHost("192.168.1.2")
            .setConnectTimeout(5000)
            .setPort(5222)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible)
            .build()
    }

    @Singleton
    @Provides
    fun provideXMPPTCPConnection(
        configuration: XMPPTCPConnectionConfiguration
    ): XMPPTCPConnection {

        return XMPPTCPConnection(configuration)
    }
 }
