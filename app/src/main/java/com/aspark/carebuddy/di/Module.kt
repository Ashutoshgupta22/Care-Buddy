package com.aspark.carebuddy.di

import com.aspark.carebuddy.api.NurseApi
import com.aspark.carebuddy.api.UserApi
import com.aspark.carebuddy.repository.Repository
import com.aspark.carebuddy.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Provides
    fun provideRetrofit() : Retrofit {

        return RetrofitService.retrofit
    }


    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {

        return retrofit.create( UserApi::class.java)
    }

    @Provides
    fun provideNurseApi(retrofit: Retrofit): NurseApi {
        return retrofit.create(NurseApi::class.java)
    }

    @Provides
    fun provideRepository( userApi: UserApi, nurseApi: NurseApi) : Repository {
        return Repository(userApi, nurseApi)
    }

}