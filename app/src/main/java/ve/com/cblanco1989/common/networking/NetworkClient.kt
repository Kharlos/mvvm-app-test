package ve.com.cblanco1989.common.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient<T> {

    private val httpLoggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    private fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):
            OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer ${NetworkConstants.movieApiKey}")
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .method(original.method, original.body)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    private fun getRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getRetrofitService(interfaceType : Class<T>) : T{
        return getRetrofit(getOkHttpClient(httpLoggingInterceptor)).create(interfaceType)
    }

}