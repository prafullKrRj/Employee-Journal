package com.prafull.employeejournal.di

import com.prafull.employeejournal.data.EmployeeApiService
import com.prafull.employeejournal.data.EmployeeRepoImp
import com.prafull.employeejournal.domain.repositories.EmployeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.104.221:9090/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Provides
    @Singleton
    fun providesEmployeeApiService(retrofit: Retrofit) : EmployeeApiService {
        return retrofit.create(EmployeeApiService::class.java)
    }
    @Provides
    @Singleton
    fun providesEmployeeRepo(apiService: EmployeeApiService): EmployeeRepository {
        return EmployeeRepoImp(apiService)
    }
}