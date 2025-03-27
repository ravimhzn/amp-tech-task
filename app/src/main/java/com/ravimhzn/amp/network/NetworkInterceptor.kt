package com.ravimhzn.amp.network

import android.util.Log
import com.ravimhzn.amp.util.LOG_INFO
import com.ravimhzn.amp.util.NetworkUtil
import com.ravimhzn.amp.util.Views
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response

private const val NO_NETWORK_CONNECTION = 9999
private const val SYSTEM_ERROR = 9991

class NetworkInterceptor : Interceptor {

    private lateinit var networkUtil: NetworkUtil

    override fun intercept(chain: Interceptor.Chain): Response {
        networkUtil = NetworkUtil(Views.context())
        return try {
            val request = chain.request()
            Log.i(LOG_INFO, "###### ${request.url} ######")
            val response = chain.proceed(request)
            if(response.isSuccessful){
                Log.i(LOG_INFO, "###### ${response.networkResponse} ######")
            }

            response
        } catch (e: Exception) {
            handleError(chain, SYSTEM_ERROR, "Error :: $e")
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