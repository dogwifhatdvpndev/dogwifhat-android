package com.dogwifhat.app.di

import co.sentinel.vpn.based.app_config.AppConfig
import com.dogwifhat.app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideAppConfig(): AppConfig =
    object : AppConfig {
      override fun getAppVersion() = BuildConfig.VERSION_NAME
      override fun getPackage() = "com.dogwifhat.app"
      override fun getBaseUrl() = BuildConfig.API_URL
      override fun getBasedAppVersion(): Long = 1
      override fun getBasedApiVersion(): Long = 2
      override fun getAppToken(): String =
        "2GxLArhbzrH6krVr8Cz11a2Ao3vLN1ImIuZkuUKd6q14LyS9Dol4D7ZrmCq76fUufk1IXWVBGkokeVx3E2ydbSxxrFh7SKTnFlwAvUNmNHkQDU6Ge06xkERB6p0r8pWA"
    }
}
