package com.ssafy.network.di

import android.content.Context
import com.ssafy.common.data_store.TokenManager
import com.ssafy.network.interceptor.HeyFYInterceptor
import com.ssafy.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = BuildConfig.API_PATH

    private const val READ_TIMEOUT = 10L
    private const val WRITE_TIMEOUT = 10L
    private const val CONNECT_TIMEOUT = 10L
    private const val CACHE_SIZE = 10L * 1024 * 1024 // 10 MB

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
    ): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        @ApplicationContext context: Context,
        heyFYInterceptor: HeyFYInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .addInterceptor(heyFYInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    Timber.tag("API").d(message)
                }.apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideWussuInterceptor(
        tokenManager: TokenManager,
    ): HeyFYInterceptor {
        return HeyFYInterceptor(tokenManager)
    }
}
