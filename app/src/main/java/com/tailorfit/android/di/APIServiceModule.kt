
package com.tailorfit.android.di

import com.tailorfit.android.BuildConfig
import com.tailorfit.android.auth.AccessTokenAuthenticator
import com.tailorfit.android.auth.AccessTokenInterceptor
import com.tailorfit.android.auth.AccessTokenProvider
import com.tailorfit.android.tailorfitapp.accesstoken.AccessTokenProviderImpl
import com.tailorfit.android.tailorfitapp.apis.ExampleAPIAuthService
import com.tailorfit.android.tailorfitapp.apis.ExampleApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [LocalDataModule::class])
class APIServiceModule {

    @Provides
    @Named("ExampleAPIService")
    @Singleton
    fun provideExampleServiceHttpClient(
        upstream: OkHttpClient,
        @Named("ExampleAPIService") accessTokenProvider: AccessTokenProvider
    ): OkHttpClient {
        return upstream.newBuilder()
            .addInterceptor(AccessTokenInterceptor(accessTokenProvider))
            .authenticator(AccessTokenAuthenticator(accessTokenProvider))
            .build()
    }

    @Provides
    @Singleton
    fun provideExampleAPIAuthService(
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): ExampleAPIAuthService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ExampleAPIAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideExampleAPIService(
        @Named("ExampleAPIService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): ExampleApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ExampleApiService::class.java)
    }

    @Provides
    @Named("ExampleAPIService")
    fun provideAccessTokenProvider(accessTokenProvider: AccessTokenProviderImpl): AccessTokenProvider =
        accessTokenProvider

    @Provides
    @Singleton
    fun provideGenericOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().serializeNulls().create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
