package com.ravimhzn.amp.network

import com.ravimhzn.amp.util.LOG_ERROR
import com.ravimhzn.amp.util.LOG_INFO
import com.ravimhzn.amp.util.Logger
import com.ravimhzn.amp.util.NetworkUtil
import com.ravimhzn.amp.util.Views
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.io.IOException

private const val NO_NETWORK_CONNECTION = 9999
private const val SYSTEM_ERROR = 9991

class NetworkInterceptor : Interceptor {

    private lateinit var networkUtil: NetworkUtil

    override fun intercept(chain: Interceptor.Chain): Response {
        val networkUtil = NetworkUtil(Views.context())

        if (!networkUtil.isInternetAvailable()) {
            Logger.e(LOG_ERROR, "Network Unavailable. Please check your connection.")
        }

        return try {
            val request = chain.request()
            Logger.d(LOG_INFO, "Request: ${request.method} ${request.url}")

            val response = chain.proceed(request)

            // Log only a portion of the response body to avoid performance issues
            val responseBody = response.peekBody(Long.MAX_VALUE).string()
            Logger.d(LOG_INFO, "Response Code: ${response.code}\nBody: ${responseBody.take(500)}...")

            response
        } catch (e: IOException) {
            Logger.e(LOG_ERROR, "Network error: ${e.localizedMessage}")
            handleError(chain, SYSTEM_ERROR, "Network error")
        } catch (e: Exception) {
            Logger.e(LOG_ERROR, "Unexpected error: ${e.localizedMessage}")
            handleError(chain, SYSTEM_ERROR, "Unexpected error")
        }
    }


    private fun handleError(chain: Interceptor.Chain, errorCode: Int, message: String) =
        Response.Builder()
            .code(errorCode)
            .protocol(Protocol.HTTP_2)
            .message(message)
            .request(chain.request())
            .build()
}