package com.example.lookup.di

import com.example.lookup.BuildConfig
import com.example.lookup.data.remote.languagemodels.textgenerator.TextGeneratorClient
import com.example.lookup.data.remote.languagemodels.textgenerator.TextGeneratorClientConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * The default interceptor for [TextGeneratorClient] used to set the authorization header
 * for each request and also the connect and read timeout.
 * Note : Since the [TextGeneratorClient] interacts with an LLM (which is inherently slow at the
 * time of writing this application) over a network (which is relatively slow), the default
 * connect and read timeout is not sufficient, and causes an [SocketTimeoutException]. Hence,
 * the default connect timeout has to be changed.
 */
private val lookupAppTextGeneratorClientOkHttpClient by lazy {
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.OPEN_AI_API_TOKEN}")
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()
}

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    fun provideTextGeneratorClient(): TextGeneratorClient = Retrofit.Builder()
        .baseUrl(TextGeneratorClientConstants.BASE_URL)
        .client(lookupAppTextGeneratorClientOkHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TextGeneratorClient::class.java)
}