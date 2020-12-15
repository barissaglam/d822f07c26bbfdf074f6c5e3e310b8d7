package barissaglam.challenge.di.modules

import android.content.Context
import barissaglam.challenge.App
import barissaglam.challenge.base.di.ApiInterceptor
import barissaglam.challenge.data.remote.RemoteApiService
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @get:Provides
    @get:Singleton
    val provideRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()

    @get:Provides
    @get:Singleton
    val provideConvertFactory:Converter.Factory = GsonConverterFactory.create(Gson())

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(@ApplicationContext appContext: Context): OkHttpClient.Builder {
        val cacheSize = (10 * 1024 * 1024).toLong()

        return OkHttpClient.Builder()
            .readTimeout(15.toLong(), TimeUnit.SECONDS)
            .connectTimeout(15.toLong(), TimeUnit.SECONDS)
            .cache(Cache(appContext.cacheDir, cacheSize))
    }


    @Provides
    @Singleton
    fun provideApi(@ApplicationContext appContext: Context, builder: Retrofit.Builder, okHttpClientBuilder: OkHttpClient.Builder, converterFactory: Converter.Factory, interceptor: ApiInterceptor): RemoteApiService {
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
            okHttpClientBuilder.addInterceptor(ChuckInterceptor(appContext))
        }
        okHttpClientBuilder.addInterceptor(interceptor)
        val client = okHttpClientBuilder.build()
        return builder.client(client)
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(converterFactory)
            .build()
            .create(RemoteApiService::class.java)
    }
}