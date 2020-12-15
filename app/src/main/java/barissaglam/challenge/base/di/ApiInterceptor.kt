package barissaglam.challenge.base.di

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder()
        requestBuilder.addHeader(name = "Cache-Control", value = "public, max-stale=36000")
        return chain.proceed(requestBuilder.build())
    }
}